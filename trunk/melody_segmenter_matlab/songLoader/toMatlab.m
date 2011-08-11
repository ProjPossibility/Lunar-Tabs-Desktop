function trackCell = toMatlab(tracksMap)

%get keys
keys = tracksMap.keySet().toArray();

%convert
for i=1:keys.length
    key = keys(i);
    melList = tracksMap.get(key);
    
    %set up map
    idMap = containers.Map('id',1);
    remove(idMap,'id');
    cnt = 1;
    
    %iterate through chords
    chordsCell = {};
    melArr = [];
    melodString = [];
    for j=0:(melList.size()-1)
        chord = melList.get(j);
        if(~isKey(idMap,chord))
            chordsCell{cnt} = chord;
            idMap(chord) = cnt;
            cnt = cnt + 1;
        end
        melArr = [melArr; idMap(chord)];            
        melodString = [melodString, char(idMap(chord)+64)];
    end
    trackCell.melodyStrs{i} = melodString;
    trackCell.keys{i} = key;
    trackCell.mels{i} = melArr;
    trackCell.chords{i} = chordsCell; 
end



