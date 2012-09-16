%% params

%user params
TRACK_NUM_PARAM = 5;
REDO_LOAD = true;
REDO_REPRESENTATION = true;
REDO_SEGMENTATION = true;

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
    tic
    [ngrams,stats,grams] = getAllNGrams(str);
    toc
    
    %get filters
    params.S = str;
    params.MIN_LENGTH = 5;
    params.MAX_LENGTH = inf;
    params.MIN_REPEAT = 2;
    params.MAX_REPEAT = inf;  
    length_filt = gramfilt(stats,grams,params,LENGTH_FILT);
    repeat_filt = gramfilt(stats,grams,params,REPEAT_FILT);
    %overlap_filt = gramfilt(stats,grams,params,SELF_OVERLAP_FILT);
    %explained_filt = gramfilt(stats,grams,[],EXPLAINED_FILT);

    %get scores
    %{
    sigma_scores = gramscore(stats,grams,[],SIGMA_SCORES);
    sim_scores = gramscore(stats,grams,[],SELFSIM_SCORES);
    %}
    selection_scores = gramscore(stats,grams,params,SELECTION_SCORES);
    
    %save
%    save('scores.mat','overlap_filt','explained_filt','selection_scores','grams','stats','ngrams','str','mel');

else
    
    %just load
    load('scores.mat','overlap_filt','explained_filt','selection_scores','grams','stats','ngrams','str','mel');
end

%% create representation
if(REDO_REPRESENTATION)
    
    %total filter
%    total_filt = length_filt & repeat_filt & overlap_filt & explained_filt;
     total_filt = length_filt & repeat_filt;
    selection_scores(~total_filt) = -inf;
    [newOrder,oldOrder] = sort(selection_scores,'descend');
    new_indx = oldOrder(1:25);
    
    %apply total filter
    gramsNew = cell(length(new_indx),1);
    for i=1:length(new_indx)
        gramsNew{i} = grams{new_indx(i)};
    end
    statsNew = stats(new_indx,:);    
    
       
    %create action masks
    actionMasks = generateActionMask([1:length(gramsNew)]', grams, str);
    
    %save
    save('represent.mat','actionMasks');
    
else
    
    %just load
    load('represent.mat','actionMasks');
end

%% apply segmentation
if(REDO_SEGMENTATION)

    %brute force puzzle search
    tic
    [finStates, finActSacks]  = genSegs(actionMasks);
    toc

    %generate scores for segmentations and find best
    tic
    seg.grams = gramsNew;
    seg.stats = statsNew;
    seg.str = str;
    seg.mel = mel;
    scores = zeros(length(finActSacks),1);
    for i=1:length(finActSacks)
        scores(i) = optSeg(find(finActSacks{i}),seg);
    end
    [minVal,minInd] = min(scores);
    actSack = finActSacks{minInd};
    toc
        
    %show segmentation
    showSeg(actSack,seg);

else
end