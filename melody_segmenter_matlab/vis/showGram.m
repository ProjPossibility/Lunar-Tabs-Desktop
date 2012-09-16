%function Z = showGram(str, mel, grams, gramIDs,extractor)
function Z = showGram(str, mel, grams, gramIDs)

%globals
%global TRACK_NUM;

%plot melody
figure(42);
hold on;
plot(mel);

%plot segmented grams
for j=1:length(gramIDs)
    
    %get gram id
    gramID = gramIDs(j);
        
    %get occurences
    occurStart = findstr(str, grams{gramID});

    %plot
    for i=1:length(occurStart)
        rectangle('Position',[occurStart(i),1,length(grams{gramID})-1,10])
%        extractor.getClip('melody_segmenter_matlab/sampleTabs/breakfast_at_tiffanys.gp3', ['test_',num2str(TRACK_NUM),'_',num2str(j),'_',num2str(i),'.mid'],TRACK_NUM, occurStart(i)-1,occurStart(i)+length(grams{gramID})-1);
    end
    
end    
hold off;
getframe(gcf);

Z=1;
