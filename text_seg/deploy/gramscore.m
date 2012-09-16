function scores = gramscore(stats,grams,params,SCORE_ID)

%% params

%Score IDs
SIGMA_SCORES=1;
SELFSIM_SCORES=2;
SELECTION_SCORES=3;

%% Scoring Schemes
scores = zeros(size(stats,1),1);

%sigma scores 
if(SCORE_ID==SIGMA_SCORES)
    N = max(stats(:,1));
    for i=1:N
        pts = stats(stats(:,1)==i,:);
        meanCounts = mean(pts(:,2));
        stdCounts = std(pts(:,2));
        scores(stats(:,1)==i) = (pts(:,2) -  meanCounts) / (stdCounts);
    end
    scores(isnan(scores) | scores <= 0)=0;
    scores = scores / sum(scores);
end

%self sim
if(SCORE_ID==SELFSIM_SCORES)
    for i=1:length(grams)
        scores(i) = selfsim(grams{i});
    end
end

%selection score
if(SCORE_ID==SELECTION_SCORES)
    
    %params
    a=1;
    b=2;
    c=3;
    S = params.S;    
    
    %scores
    for i=1:length(grams)
        L = stats(i,1);
        F = stats(i,2);
        T= L*F;
        used = false(length(S),1);
        occ = strfind(S,grams{i});
        for j=1:length(occ)
            used(occ(j):(occ(j)+length(grams{i})-1)) = true;
        end
        U = sum(used==true);
        DOL = (T-U) / U;
        scores(i) = L^a * F^b / 10^(c*DOL);
    end
end
