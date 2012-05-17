function [extractor,tracksMap] = loadJava()

%set up java class path
javaaddpath(fullfile('melody_segmenter_matlab/songloader/','SongExtractor.jar'));
import api.tuxguitar.MelodyExtractor.*;

%load melody extractor
extractor = TGMelodyExtractor();

%load song
tracksMap = extractor.getStringsforTracks('melody_segmenter_matlab/sampleTabs/breakfast_at_tiffanys.gp3');
