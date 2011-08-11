function actionMasks = generateActionMask(rankedList, grams, str)

actionMasks = {};
for i=1:length(rankedList)
        
    %get gram occurences
    gram = grams{rankedList(i,1)};
    occurences = strfind(str,gram);
    
    %create action mask
    actionMask = zeros(length(str),1);
    for j=1:length(occurences)
        cOc = occurences(j);
        for k=cOc:(cOc+length(gram)-1)
            actionMask(k) = 1;
        end
    end
    
    %store
    actionMasks{i} = actionMask;    
end