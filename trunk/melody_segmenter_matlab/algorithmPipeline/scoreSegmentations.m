function scores = scoreSegmentations(finStates, finActSacks, grams)

%size calc
A = length(finStates);
scores = zeros(A,11);

%score
for i=1:A
    
    %selected grams
    finState = finStates{i};
    gramIndices = 1:length(grams);
    gramIndices = gramIndices(finActSacks{i}==1);
    selectedGrams = grams(gramIndices);
    
    %score 1 -- original index
    scores(i,1) = i;
    
    %score 2 -- coverage amount
    scores(i,2) = sum(finState);
    
    %score 3 -- number of grams used.
    scores(i,3) = length(selectedGrams);
    
    %score 4,5,6,7 -- stats about n-grams used
    Ndist = zeros(length(selectedGrams),1);
    for j=1:length(selectedGrams)
        Ndist(j) = length(selectedGrams{j});
    end
    scores(i,4) = mean(Ndist);
    scores(i,5) = max(Ndist);
    scores(i,6) = min(Ndist);
    scores(i,7) = std(Ndist);
    
    %scores 8,9,10,11 - gap size stats
    Gdist = [];
    lastBit=1;    
    cGap = 0;
    for j=1:length(finState)
        if(finState(j)==0)
            cGap = cGap + 1;
        elseif(finState(j)==1 && lastBit==0)
            Gdist = [Gdist;cGap];
            cGap=0;
        end
        lastBit = finState(j);
    end
    scores(i,8) = mean(Gdist);
    scores(i,9) = max(Gdist);
    scores(i,10) = min(Gdist);
    scores(i,11) = std(Gdist);
    
end