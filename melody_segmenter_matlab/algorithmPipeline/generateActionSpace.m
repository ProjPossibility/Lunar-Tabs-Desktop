function actionSpace = generateActionSpace(str)

%return structure
N = length(str);
actionSpace = cell(N,1);

%generate loop
for i=1:N
    
    actionSpace{i} = getNGrams(str,i);

    %stop if getting single gram because that's useless.
    if(actionSpace{i}.length==1)
        i
        SINGLE_GRAM = 1
        break;
    end
    
    %stop if getting no repetition
    values = actionSpace{i}.values;
    valMax=0;
    for j=1:length(values)
        if(values{j} > valMax)
            valMax = values{j};
        end
    end
    if(valMax<2)
        i
        NO_REPETITION = 1
        break;
    end
    
end

%reduce size
actionSpace = actionSpace(1:(i-1));

