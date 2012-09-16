%% params

%user params
TRACK_NUM=5;
weights = [0,0.5,0,0.5];

%FILT IDs
LENGTH_FILT = 1;
REPEAT_FILT = 2;
SELF_OVERLAP_FILT = 3;
EXPLAINED_FILT = 4;

%SCORE IDs
SIGMA_SCORES=1;
SELFSIM_SCORES=2;

%% load

%load
trackCell = loadJava2();

%% create basic profiles 

%create pitch profile
pitchIntervals = trackCell.pitches{TRACK_NUM};
p1 = LBDM_strengths(pitchIntervals);

%create rest profile
restIntervals = trackCell.durs{TRACK_NUM};
restInd = (trackCell.mels{TRACK_NUM}==1);
restIntervals(~restInd)=0;
p2 = LBDM_strengths(restIntervals);

%create interonset interval profile
ioiIntervals = trackCell.durs{TRACK_NUM};
ioiIntervals(restInd)=0;
p3 = LBDM_strengths(ioiIntervals);

%% create rep profile

%compute ngrams, filter, and score.
str = trackCell.melodyStrs{TRACK_NUM};
tic
[ngrams,stats,grams] = getAllNGrams(str);
toc
params.S = str;
params.MIN_LENGTH = 2;
params.MAX_LENGTH = inf;
length_filt = gramfilt(stats,grams,params,LENGTH_FILT);
overlap_filt = gramfilt(stats,grams,params,SELF_OVERLAP_FILT);
explained_filt = gramfilt(stats,grams,[],EXPLAINED_FILT);
sigma_scores = gramscore(stats,grams,[],SIGMA_SCORES);
sim_scores = gramscore(stats,grams,[],SELFSIM_SCORES);

%apply total filter
total_filt = length_filt & overlap_filt & explained_filt;
new_indx = find(total_filt);
gramsNew = cell(length(new_indx),1);
for i=1:length(new_indx)
    gramsNew{i} = grams{new_indx(i)};
end
statsNew = stats(total_filt,:);    
sigma_scores_new = sigma_scores(total_filt);
sim_scores_new = sim_scores(total_filt);
sim_scores_new = max(sim_scores_new) - sim_scores_new; %invert
sim_scores_new = sim_scores_new ./ sum(sim_scores_new); %normalize

%kernelize top grams (weighted by sim score)
p4 = zeros(length(new_indx),length(str));
for i=1:length(new_indx)
    occurences = strfind(str,gramsNew{i});
    for j=1:length(occurences)
        p4(i,occurences(j)) = sim_scores_new(i);
        p4(i,(occurences(j)+length(gramsNew{i})-1)) = sim_scores_new(i);
    end
end

%% total profile

%combine
p_total = weights(1)*p1 + weights(2)*p2 + weights(3)*p3 + weights(4)*p4;

%% segment

%find boundaries
maxima = findBoundaries(p_total);
numBounds = sum(maxima)-1;
maxima = find(maxima);
grms = zeros(numBounds,length(p3));
for i=1:numBounds
    startV = maxima(i);
    endV = maxima(i+1);
    grms(i,startV:endV) = trackCell.mels{TRACK_NUM}(startV:endV);
end