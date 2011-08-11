function [rankedList, grams] = rankNGrams(C, NGRAM_LIM)

%get count
cnt = 0;
for i=1:length(C)
    keys = C{i}.keys;
    for j=1:length(keys)
        cnt = cnt + 1;
    end
end

%create listing
rankedList = zeros(cnt,4);
grams = cell(cnt,1);
cnt=1;
for i=1:length(C)
    keys = C{i}.keys;
    for j=1:length(keys)
        
        %put in structure
        grams{cnt} = keys{j};
        
        %score 1 -- original index
        rankedList(cnt,1) = cnt;
        
        %score 2 -- length of gram (n)
        rankedList(cnt,2) = length(keys{j});

        %score 3 -- number of grams
        rankedList(cnt,3) = C{i}(keys{j});
        
        %score 4 -  product of scores
        rankedList(cnt,4) = rankedList(cnt,3) * rankedList(cnt,2);
        
        %update
        cnt = cnt + 1;
        
    end
end

%rank according to product and keep top 20 or less. 
rankedList = sortrows(rankedList,-4);
if(size(rankedList,1) > NGRAM_LIM)
    rankedList = rankedList(1:NGRAM_LIM,:);
end
