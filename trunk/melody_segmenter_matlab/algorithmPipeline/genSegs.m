function [finStates, finActSacks] = genSegs(actionMasks)

%size calc
STATE_LENGTH = length(actionMasks{1});
SACK_LENGTH = length(actionMasks);

%state
state = zeros(STATE_LENGTH,1);
actSack = zeros(1,SACK_LENGTH);

%global memory
global finStates;
global finActSacks;
global solCtr;
global ctr;
ctr=0;
finStates = {};
finActSacks = {};
solCtr = 1;

%set off search
expandState(state, actSack, actionMasks);

