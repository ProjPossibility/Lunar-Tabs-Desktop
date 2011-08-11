function ngrams = getNGrams(str, n)

%set up ngrams
ngrams = containers.Map('str',1);
remove(ngrams,'str');

%length
for i=1:length(str)
    
    %no more tokens
    if(i+n > length(str))
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