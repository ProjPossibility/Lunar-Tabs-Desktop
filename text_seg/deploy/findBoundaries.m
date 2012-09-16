function maxima = findBoundaries(arr)

%find maxima
maxima = false(length(arr),1);
for i=1:length(arr)
    if(i==1 || i==length(arr))
        maxima(i) = true;
    else
        maxima(i) = (arr(i) > arr(i-1) & arr(i) > arr(i+1));
    end
end