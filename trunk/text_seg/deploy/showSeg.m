function showSeg(actSack,seg,maxima)

%extract
grams = seg.grams;
str = seg.str;
mel = seg.mel;
actSackInd = find(actSack);

%get bounds
bounds = zeros(length(mel),1);
for i=1:length(actSackInd)
    occ = findstr(str,grams{actSackInd(i)});
    for j=1:length(occ)
        bounds(occ(j))=1;
        bounds((occ(j)+length(grams{actSackInd(i)})))=1;
    end
end

%add in lmdb constraints
bounds(maxima==1)=1;

%show
plot(1:length(mel),mel,1:length(bounds),max(mel)*bounds,'s--');

