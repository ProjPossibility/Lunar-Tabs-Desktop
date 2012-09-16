function [p,q,grams]=maxprob(S,D)

%init
n=length(S);
p = zeros(n,1);
q = cell(n,1);

%forward trace
for i=1:n
    inds = fliplr(1:i);
    maxProb=0;
    maxProbW = '';
    for j=1:i
        
        %get current word
        ind = inds(j);
        w = S(ind:i);

        %look up in dictionary
        cp = get_PO(w,D,false);
        if(ind-1==0)
            prior = 1;
        else
            prior = p(ind-1);
        end
        if(cp*prior > maxProb)
            maxProb = cp*prior;
            maxProbW = w;
        end
    end
    p(i) = maxProb;
    q{i} = maxProbW;
end

%back trace to find grams
i=length(p);
grams = [];
%{
while(i>=1)
    grams = [grams;i];
    L = length(q{i});
    i = i-L;
    i
end
%}