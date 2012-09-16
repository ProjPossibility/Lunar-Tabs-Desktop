function [ngrams,stats,grams] = getAllNGrams_heu(str)

%set up ngrams
ngrams = containers.Map;

%length
for nInd=1:length(str)
    n = (length(str) - nInd) + 1;
    keys2 = ngrams.keys;
    levelSet = containers.Map;   
    for i=1:length(str)

        %no more tokens
        if((i+n-1) > length(str))
            break;
        end

        %construct gram
        gram = str(i:(i+n-1));        
        
        %update gram count
        if(~isKey(ngrams,gram))
            ngrams(gram) = 1;
            levelSet(gram) = 1;
        else
            ngrams(gram) = ngrams(gram) + 1;    
            levelSet(gram) = levelSet(gram) + 1;
        end
    end
    
    %prune grams based on level set
    keys = levelSet.keys;
    for k=1:length(keys)
        exclude = false;
        for j=1:length(keys2)
            if(ngrams(keys2{j})==levelSet(keys{k}) && ~isempty(strfind(keys2{j},keys{k})))
                exclude = true;
                break;
            end
        end
        if(exclude)
            remove(ngrams,keys{k});
        end
    end
    
end

%create stats table
keys = ngrams.keys;
grams = cell(length(keys),1);
stats = zeros(length(keys),2);
for i=1:length(keys)
    grams{i} = keys{i};
    stats(i,1) = length(grams{i});
    stats(i,2) = ngrams(grams{i});
end