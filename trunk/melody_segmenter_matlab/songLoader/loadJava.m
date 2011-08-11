%set up java class path
javaaddpath(fullfile('/Users/prateek/Desktop/tab_project/','SongExtractor.jar'));
import api.tuxguitar.MelodyExtractor.*;

%load melody extractor
extractor = TGMelodyExtractor();

%load song
tracksMap = extractor.getStringsforTracks('/Users/prateek/Desktop/tab_project/breakfast_at_tiffanys.gp3');
