function [p,q,gramsInd]=maxprob_logs(S,D)

%init
n=length(S);
p = zeros(n,1);
q = cell(n,1);

%forward trace
for i=1:n
    inds = fliplr(1:i);
    maxProb=-inf;
    maxProbW = '';
    for j=1:i
        
        %get current word
        ind = inds(j);
        w = S(ind:i);

        %look up in dictionary
        cp = get_PO(w,D,true);
        if(ind-1==0)
            prior = 0;
        else
            prior = p(ind-1);
        end
        if(cp+prior > maxProb)
            maxProb = cp+prior;
            maxProbW = w;
        end
    end
    p(i) = maxProb;
    q{i} = maxProbW;
end

%back trace to find grams
i=length(p);
gramsInd = [];
while(i>=1)
    L = length(q{i});
    gramsInd = [gramsInd;i];
    i = i-L;
end