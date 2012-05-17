function showSegmentation(str, mel, grams, actSack, trueInds,extractor)

%extract gram indices
indices = 1:length(actSack);
indices = indices(actSack==1);
indices = trueInds(indices);

%show grams
showGram(str, mel, grams, indices,extractor);