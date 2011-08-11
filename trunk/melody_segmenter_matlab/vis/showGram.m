function Z = showGram(str, mel, grams, gramIDs)

figure(42);
hold on;
plot(mel);

for j=1:length(gramIDs)
    
    %get gram id
    gramID = gramIDs(j);
        
    %get occurences
    occurStart = findstr(str, grams{gramID});

    %plot
    for i=1:length(occurStart)
        rectangle('Position',[occurStart(i),1,length(grams{gramID})-1,100])
    end
end    
hold off;
getframe(gcf);

Z=1;
