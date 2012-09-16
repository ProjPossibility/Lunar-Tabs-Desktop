function p = get_PO(S,D,lFlag)

if(~lFlag)

    %base case
    if(isempty(S))
        p=1.0;
        return;
    end

    %else
    p=0.0;
    if(D.isKey(S))
        p=D(S);
    end
    
else
    
    %base case
    if(isempty(S))
        p=0;
        return;
    end

    %else
    p=-inf;
    if(D.isKey(S))
        p=D(S);
    end

    
end