function [finStates, finActSacks] = testCase()

%fake world
actionMasks{1} = [1 0 0 0];
actionMasks{2} = [0 1 0 0];
actionMasks{3} = [0 0 1 0];
actionMasks{4} = [0 0 0 1];
actionMasks{5} = [1 1 0 0];
actionMasks{6} = [0 1 1 0];
actionMasks{7} = [0 0 1 1];
actionMasks{8} = [1 1 1 0];
actionMasks{9} = [0 1 1 1];
actionMasks{10} = [1 1 1 1];

%state
state = zeros(1,4);
actSack = zeros(1,10);

%global memory
global finStates;
global finActSacks;
global solCtr;
finStates = {};
finActSacks = {};
solCtr = 1;

%set off search
expandState(state, actSack, actionMasks);

