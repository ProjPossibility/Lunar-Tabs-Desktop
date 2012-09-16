%% params

%user params
TRACK_NUM_PARAM = 5;
REDO_LOAD = false;
REDO_REPRESENTATION = true;
REDO_SEGMENTATION = false;

%FILT IDs
LENGTH_FILT = 1;
REPEAT_FILT = 2;
SELF_OVERLAP_FILT = 3;
EXPLAINED_FILT = 4;

%SCORE IDs
SIGMA_SCORES=1;
SELFSIM_SCORES=2;
SELECTION_SCORES = 3;

%% load
if(REDO_LOAD)

    %load file
    load bat.mat;
    TRACK_NUM=TRACK_NUM_PARAM;
    trackCell = toMatlab(tracksMap);
    str = trackCell.melodyStrs{TRACK_NUM};
    mel = trackCell.mels{TRACK_NUM};

    %get ngrams
    [ngrams,stats,grams] = getAllNGrams(str);

    %get filters
    params.S = str;
    params.MIN_LENGTH = 5;
    params.MAX_LENGTH = inf;
    params.MIN_REPEAT = 2;
    params.MAX_REPEAT = inf;  
    length_filt = gramfilt(stats,grams,params,LENGTH_FILT);
    repeat_filt = gramfilt(stats,grams,params,REPEAT_FILT);
    overlap_filt = gramfilt(stats,grams,params,SELF_OVERLAP_FILT);
    explained_filt = gramfilt(stats,grams,[],EXPLAINED_FILT);

    %get scores
    sigma_scores = gramscore(stats,grams,[],SIGMA_SCORES);
    sim_scores = gramscore(stats,grams,[],SELFSIM_SCORES);
    selection_scores = gramscore(stats,grams,params,SELECTION_SCORES);
    
    %save
    save('scores.mat','overlap_filt','explained_filt','sigma_scores','sim_scores','grams','stats','ngrams','str','mel');
else
    
    %just load
    load('scores.mat','overlap_filt','explained_filt','sigma_scores','sim_scores','grams','stats','ngrams','str','mel');
end

%% create representation
if(REDO_REPRESENTATION)
    
    %total filter
    total_filt = length_filt & repeat_filt & overlap_filt & explained_filt;
    new_indx = find(total_filt);
    uniIndex = find(stats(:,1)==1);
    
    %apply total filter
%    gramsNew = cell(length(uniIndex)+length(new_indx),1);
    gramsNew = cell(length(new_indx),1);
    cnt=1;
    %{
    for i=1:length(uniIndex)
        gramsNew{cnt} = grams{uniIndex(i)};
        cnt = cnt + 1;
    end
    %}
    for i=1:length(new_indx)
        gramsNew{cnt} = grams{new_indx(i)};
        cnt = cnt + 1;
    end
    statsNew = stats(total_filt,:);    
    sigma_scores_new = sigma_scores(total_filt);
    sim_scores_new = sim_scores(total_filt);
    sim_scores_new = max(sim_scores_new) - sim_scores_new; %invert
    selection_scores_new = selection_scores(total_filt);
    
    %create probabilties
%    NERF_WEIGHT = max(sim_scores_new(~isinf(sim_scores_new) & sim_scores_new > 0));
%    probs = [NERF_WEIGHT*ones(length(uniIndex),1); sim_scores_new];
    NERF_WEIGHT = 10^-5;
    probs = [NERF_WEIGHT*ones(length(uniIndex),1);selection_scores_new];
    probs = probs ./ sum(probs);
    probs = log(probs);

    %create dictionary
    dict = containers.Map;
    for i=1:length(gramsNew)
        dict(gramsNew{i}) = probs(i);
    end
    
    %save
    save('represent.mat','dict','probs','total_filt','gramsNew','str');
    
else
    
    %just load
    load('represent.mat','dict','str');
end

%% apply segmentation
if(REDO_SEGMENTATION)
   
   %run
   [p,q,grams2]=maxprob_logs(str,dict); 
    
   
   %save
   save('segment.mat','p','q','grams2');
   
else
end