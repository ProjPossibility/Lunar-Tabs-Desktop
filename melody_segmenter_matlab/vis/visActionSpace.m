function visActionSpace(actionSpace)

%plot n-gram length by number of grams
figure(1);
A = length(actionSpace);
numGrams = zeros(A,1);
gramLength = zeros(A,1);
for i=1:A
    keys = actionSpace{i}.keys;
    numGrams(i) = actionSpace{i}.length;
    gramLength(i) = length((keys{1}));
end
plot(gramLength,numGrams,'s');
xlabel('Length of Gram');
ylabel('Number of Grams');

%plot n-gram length by average gram count
figure(2);
distMean = zeros(A,1);
distStd = zeros(A,1);
for i=1:A
    keys = actionSpace{i}.keys;
    dist = zeros(length(keys),1);
    for j=1:length(keys)
        dist(j) = actionSpace{i}(keys{j});
    end
    distMean(i) = mean(dist);
    distStd(i) = std(dist);    
end
errorbar(gramLength,distMean,distStd);
%plot(gramLength,distMean,'s');
xlabel('Length of Gram');
ylabel('Average Gram Count');

%tradeoff curve?
figure(3);
plot(numGrams,distMean,'s');
xlabel('Number of Grams');
ylabel('Average Gram Count');

%size calc
NUM_NODES = 0;
for i=1:length(actionSpace)
    keys = actionSpace{i}.keys;
    for j=1:length(keys)
        NUM_NODES = NUM_NODES + 1;
    end
end
NUM_NODES

%make grams list
gramsList = cell(NUM_NODES,1);
coordsList = zeros(NUM_NODES,2);
cnt=1;
for i=1:length(actionSpace)
    keys = actionSpace{i}.keys;
    for j=1:length(keys)
        
        %continue
        if(actionSpace{i}(keys{j}) < 2)
            continue;
        end
        
        %make list
        gramsList{cnt} = keys{j};
        coordsList(cnt,1) = j;
        coordsList(cnt,2) = i; 
        cnt = cnt + 1;
    end
end

%create sparse matrix
adjMat = zeros(NUM_NODES,NUM_NODES);
for i=1:length(gramsList) 
    needle = gramsList{i};
    for j=(i+1):length(gramsList)        
        
        %update adj mat
        hay = gramsList{j};
        A = strfind(hay,needle);
        if(~isempty(A))
            adjMat(i,j) = 1;
        end
    end     
end

%plot graph
figure(4);
gplot(adjMat,coordsList);
title('Graph rep');