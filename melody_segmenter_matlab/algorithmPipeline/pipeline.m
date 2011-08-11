%params
TRACK_NUM=1;
MIN_NGRAM_SIZE = 7;
MAX_NGRAM_SIZE = 60;
NGRAM_LIM=20;

%load into matlab
tic
loadJava();
trackCell = toMatlab(tracksMap);
str = trackCell.melodyStrs{TRACK_NUM};
mel = trackCell.mels{TRACK_NUM};
toc

%create action space
tic
actionSpace = generateActionSpace(str);
toc

%prune action space
tic
actionSpace = pruneActionSpace(actionSpace,str,MIN_NGRAM_SIZE, MAX_NGRAM_SIZE);
toc

%vis
visActionSpace(actionSpace);

%rank grams and generate action masks
tic
[rankedList, grams] = rankNGrams(actionSpace, NGRAM_LIM);
actionMasks = generateActionMask(rankedList, grams, str);
toc

%Brute force puzzle search
tic
[finStates, finActSacks]  = genSegs(actionMasks);
toc

%generate scores for segmentations and sort
tic
scores = scoreSegmentations(finStates, finActSacks, grams);
scores = sortrows(scores,[-2,-10]);
toc

%show result
figure(42);
showSegmentation(str, mel, grams, finActSacks{scores(1,1)})