package InstructionGenerator;
import java.io.FileInputStream;

import org.herac.tuxguitar.io.base.TGSongLoader;
import org.herac.tuxguitar.song.factory.TGFactory;
import org.herac.tuxguitar.song.models.TGBeat;
import org.herac.tuxguitar.song.models.TGMeasure;
import org.herac.tuxguitar.song.models.TGNote;
import org.herac.tuxguitar.song.models.TGSong;
import org.herac.tuxguitar.song.models.TGTrack;
import org.herac.tuxguitar.song.models.TGVoice;
import java.util.*;

public class SongLoader {
	
	public static List<TGNote> getNotesForBeat(TGBeat beat) {
		List<TGNote> rtn = new ArrayList<TGNote>();
		for(int x=0; x < beat.countVoices(); x++) {
			TGVoice currentVoice = beat.getVoice(x);
			for(int y=0; y < currentVoice.countNotes(); y++) {
				rtn.add(currentVoice.getNote(y));
			}
		}
		return rtn;
	}
	
	public static List<TGBeat> getBeatsforSelection(List<TGMeasure> measures, int m0, int mf) {
		List<TGBeat> rtn = new ArrayList<TGBeat>();
		for(int x=m0; x < mf; x++) {
			TGMeasure measure = measures.get(x);
			for(int y=0; y < measure.countBeats(); y++) {
				rtn.add(measure.getBeat(y));
			}
		}		
		return rtn;
	}
	
	public static List<TGMeasure> getMeasures(TGTrack track) {
		List<TGMeasure> rtn = new ArrayList<TGMeasure>();
		for(int x=0; x < track.countMeasures(); x++) {
			rtn.add(track.getMeasure(x));
		}
		return rtn;
	}
	
	public static TGTrack getTrack(TGSong song, int trackIndex) {
		return song.getTrack(trackIndex);
	}
	
	public static TGSong loadSong(String file) {
		TGSongLoader l = new TGSongLoader();
		try {
			return l.load(new TGFactory(), new FileInputStream(file));
		}
		catch(Exception e) {
			System.out.println("Song could not be loaded. See error trace.");
			e.printStackTrace();
			return null;
		}
	}
}
