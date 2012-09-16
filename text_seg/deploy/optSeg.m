function score = optSeg(sackInd,seg)

%% user params
weights = [100;1/4*ones(4,1)];

%% extract

%extract
grams = seg.grams;
gramsNew={};
for i=1:length(sackInd)
    gramsNew{i} = grams{sackInd(i)};
end
grams = gramsNew;
stats = seg.stats(sackInd,:);
str = seg.str;

%% DESTROY cases

%flag
destroy=false;

%overlapping grams chosen
coverageVect = zeros(length(str),1);
for i=1:length(grams)
    occ = findstr(str,grams{i});
    for j=1:length(occ)
        coverageVect(occ(j):(occ(j)+length(grams{i})-1)) = coverageVect(occ(j):(occ(j)+length(grams{i})-1)) + 1;
    end
end
if(sum(coverageVect>2)>0)
    destroy=true;
end

%destroy
if(destroy)
    score=inf;
    return;
end

%% score

%coverage score (upweight)
coverageVect = zeros(length(str),1);
for i=1:length(grams)
    occ = findstr(str,grams{i});
    for j=1:length(occ)
        coverageVect(occ(j):(occ(j)+length(grams{i})-1)) = 1;
    end
end
COVERAGE_SCORE = sum(coverageVect);

%number of n-grams used (upweight)
NUMBER_GRAMS_SCORE = size(grams,1);

%similarity between ngrams (penalize)
intersim = zeros(length(grams),length(grams));
for i=1:length(grams)
    for j=1:length(grams)
        intersim(i,j) = edit_distance_levenshtein(grams{i},grams{j});
    end
end
SUM_INTERSIM_SCORE = -1*sum(sum(intersim));

%self similarity score (penalize)
simScores = gramscore(stats,grams,[],2);
MEAN_SIM_SCORE = -1*mean(simScores);
MAX_SIM_SCORE = -1*max(simScores);

%% combine scores to compute final score

score_v = [COVERAGE_SCORE,NUMBER_GRAMS_SCORE,SUM_INTERSIM_SCORE,MEAN_SIM_SCORE,MAX_SIM_SCORE];
score = -1*dot(weights,score_v);