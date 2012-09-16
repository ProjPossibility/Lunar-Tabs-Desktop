%% params

%user params
TRACK_NUM = 5;
NUM_GRAMS_TO_KEEP=25;
REDO_LOAD = true;
REDO_REPRESENTATION = true;
REDO_SEGMENTATION = true;
file = 'melody_segmenter_matlab/sampleTabs/breakfast_at_tiffanys.gp3';
%file = 'melody_segmenter_matlab/sampleTabs/drops_of_jupiter.gp3';
jarPath = ['melody_segmenter_matlab/songloader/','SongExtractor2.jar'];

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
    trackCell = loadJava2(file,jarPath);
    str = trackCell.trackStrs{TRACK_NUM};
    mel = trackCell.trackInds{TRACK_NUM};
    durs = trackCell.durs{TRACK_NUM};
    restIndex = trackCell.restIndex{TRACK_NUM};
    
    %get ngrams
    tic
    [ngrams,stats,grams] = getAllNGrams(str);
    toc
    
    %get filters and scores
    params.S = str;
    params.MIN_LENGTH = 5;
    params.MAX_LENGTH = inf;
    params.MIN_REPEAT = 2;
    params.MAX_REPEAT = inf;  
    length_filt = gramfilt(stats,grams,params,LENGTH_FILT);
    repeat_filt = gramfilt(stats,grams,params,REPEAT_FILT);
    selection_scores = gramscore(stats,grams,params,SELECTION_SCORES);
    
    %save
    save('scores.mat','length_filt','repeat_filt','selection_scores','grams','stats','ngrams','str','mel','durs','restIndex');

else
    
    %just load
    load('scores.mat','length_filt','repeat_filt','selection_scores','grams','stats','ngrams','str','mel','durs','restIndex');
end

%% create representation
if(REDO_REPRESENTATION)
    
    %total filter
    total_filt = length_filt & repeat_filt;
    selection_scores(~total_filt) = -inf;
    [newOrder,oldInx] = sort(selection_scores,'descend');
    new_indx = oldInx(1:NUM_GRAMS_TO_KEEP);
    
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

    %find maxima in LBDM rest profile
    restIntervals = durs;
    restInd = (mel==restIndex);
    restIntervals(~restInd)=0;
    restProfile = LBDM_strengths(restIntervals);
    maxima = findBoundaries(restProfile);
        
    %show segmentation
    showSeg(actSack,seg,maxima);

else
end