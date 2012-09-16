function [ngrams,maxVal] = getNGrams(str, n)

%set up ngrams
ngrams = containers.Map('str',1);
remove(ngrams,'str');

%length
maxVal=0;
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
    
    %update max
    if(ngrams(gram) > maxVal)
        maxVal = ngrams(gram);
    end
    
end