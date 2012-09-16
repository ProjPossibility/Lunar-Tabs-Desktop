function [s,m] = numLZSplit(str)

m = containers.Map;
last=0;
s=0;
for i=1:length(str)
    
    %get word
    w = str((last+1):i);
    
    %if not key, add.
    if(~isKey(m,w))
        m(w) = 1;
        s = s + 1;
        last = i;
    end
end