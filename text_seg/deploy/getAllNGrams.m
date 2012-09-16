function [ngrams,stats,grams] = getAllNGrams(str)

%set up ngrams
ngrams = containers.Map('str',1);
remove(ngrams,'str');

%length
for n=1:length(str)
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
        else
            ngrams(gram) = ngrams(gram) + 1;    
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