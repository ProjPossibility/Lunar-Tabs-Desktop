function M = selfsim(str)

M=sum(xcorr(str-64,str-64)) / length(str);

%[Y,M] = getNGrams(str,1);

%get all ngrams
%[ngrams,stats] = getAllNGrams(str);

%return
%M = max(stats(:,2)) / length(str);

%{
M = zeros(3,1);
M(1,1) = max(vals);
M(2,1) = mean(vals);
M(3,1) = median(vals);
%}