function showSegmentation(str, mel, grams, actSack)

%extract gram indices
indices = 1:length(actSack);
indices = indices(actSack==1);

%show grams
showGram(str, mel, grams, indices);