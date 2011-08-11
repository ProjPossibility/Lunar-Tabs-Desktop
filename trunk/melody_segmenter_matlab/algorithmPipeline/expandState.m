function expandState(state, actSack, actionMasks)

%look through action masks to see if one can be applied.
for i=1:length(actionMasks)
        
    %see if action produces valid state.
    actionMask = actionMasks{i};
    testState = state + actionMask;
    validState = sum(testState < 2) == length(state);

    %recurse if valid state
    if(validState==1)
        testActSack = actSack;
        testActSack(i) = 1;
        expandState(testState, testActSack, actionMasks);
    end
end

%if there are no actions that produce valid states, we've reached a fin
%state. Let's put the state and action sack in global memory (*IF UNIQUE*)
global finStates;
global finActSacks;
global solCtr;
solFound=false;
if(sum(actSack)==0)
    %empty case
    return;
end
for i=1:(solCtr-1)
    if(sum(finStates{i}==state)==length(state) && sum(finActSacks{i}==actSack)==length(actSack))
        solFound = true;
        break;
    end
end
if(~solFound)
    
    %if unique sol, put into structure.
    finStates{solCtr} = state;
    finActSacks{solCtr} = actSack;
    solCtr = solCtr + 1;
end