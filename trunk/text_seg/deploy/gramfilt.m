function indic = gramfilt(stats,grams,params,FILT_ID)
tic
%% params

%FILT IDs
LENGTH_FILT = 1;
REPEAT_FILT = 2;
SELF_OVERLAP_FILT = 3;
EXPLAINED_FILT = 4;

%% Filters

%length filter
if(FILT_ID==LENGTH_FILT)
    MIN_LENGTH = params.MIN_LENGTH;
    MAX_LENGTH = params.MAX_LENGTH;
    indic = stats(:,1) >= MIN_LENGTH & stats(:,1) <= MAX_LENGTH;
end

%repeat filter
if(FILT_ID==REPEAT_FILT)
    MIN_REPEAT = params.MIN_REPEAT;
    MAX_REPEAT = params.MAX_REPEAT;
    indic = stats(:,2) >= MIN_REPEAT & stats(:,2) <= MAX_REPEAT;
end

%self overlap filter
if(FILT_ID==SELF_OVERLAP_FILT)    
    S = params.S;    
    indic = true(length(grams),1);
    for i=1:length(grams)

        %test self-overlap of gram
        gram = grams{i};
        occurences = strfind(S,gram);
        delete = false;
        for j=2:length(occurences)
            if(occurences(j) < (occurences(j-1)+length(gram)))
                delete = true;
                break;
            end
        end

        %remove
        if(delete)
            indic(i) = false;
        end
    end
end

%explained filter
if(FILT_ID==EXPLAINED_FILT)
    indic = true(length(grams),1);
    for i=1:length(grams)
        
        %search through higher levels
        delete = false;        
        higherLevelGrams = find(stats(:,1) > stats(i,1) & stats(:,2)==stats(i,2));
        for m=1:length(higherLevelGrams)
            if(~isempty(strfind(grams{higherLevelGrams(m)},grams{i})))
                delete = true;
                break;
            end
        end

        %remove
        if(delete)
            indic(i) = false;
        end            
    end    
end

toc