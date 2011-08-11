function actionSpace = pruneActionSpace(actionSpace, str, MIN_SIZE, MAX_SIZE)

%prune out range
NRange = 1:length(actionSpace);
indVect = NRange >= MIN_SIZE & NRange <= MAX_SIZE;
actionSpace = actionSpace(indVect);

%prune out n-grams that don't repeat
numGrams = zeros(length(actionSpace),1);
for i=1:length(actionSpace)
    
    %prune keys
    keys = actionSpace{i}.keys;
    for j=1:length(keys)
        if(actionSpace{i}(keys{j}) < 2)
            remove(actionSpace{i},keys{j});
        else
            numGrams(i) = numGrams(i) + actionSpace{i}(keys{j});
        end
    end        
end
indVect = numGrams > 0;
actionSpace = actionSpace(indVect);

%prune out n-grams that self-overlap
numGrams = zeros(length(actionSpace),1);
for i=1:length(actionSpace)
    
    %prune keys
    keys = actionSpace{i}.keys;
    for k=1:length(keys)
        
        %test self-overlap of gram
        gram = keys{k};
        occurences = strfind(str,gram);
        delete = false;
        for j=2:length(occurences)
            if(occurences(j) < (occurences(j-1)+length(gram)))
                delete = true;
                break;
            end
        end
        
        %actually remove
        if(delete)
            remove(actionSpace{i},keys{k});
        else
            numGrams(i) = numGrams(i) + actionSpace{i}(keys{k});
        end
    end        
end
indVect = numGrams > 0;
actionSpace = actionSpace(indVect);

%go through all grams and see whose occurences can be completely explained
%away by higher level grams that occur at same frequencies.
numGrams = zeros(length(actionSpace),1);
for i=1:length(actionSpace)
    evalKeys = actionSpace{i}.keys;
    for j=1:length(evalKeys)
        evalGram = evalKeys{j};
        evalCount = actionSpace{i}(evalKeys{j});
        delete = false;
        
        %search through higher levels
        for k=(i+1):length(actionSpace)
            cKeys = actionSpace{k}.keys;
            for m=1:length(cKeys)
                cGram = cKeys{m};
                cCount = actionSpace{k}(cKeys{m});
                if(~isempty(strfind(cGram,evalGram)))
                    if(cCount==evalCount)
                        delete = true;
                        break;
                    end
                end
            end
            
            %easy exit
            if(delete)
                break;
            end
            
        end
        
        %actually remove
        if(delete)
            remove(actionSpace{i},evalGram);
        else
            numGrams(i) = numGrams(i) + actionSpace{i}(evalGram);
        end            
    end
end
indVect = numGrams > 0;
actionSpace = actionSpace(indVect);