import java.io.FileInputStream;
import java.util.List;

import org.herac.tuxguitar.io.base.TGSongLoader;
import org.herac.tuxguitar.song.models.*;
import prateekapi.PrateekAPI;

public class OpenerTest {
	
	public static void main(String[] args) {
		PrateekAPI.say("Hello Ankit");
		PrateekAPI.say("Would you like some cake?");

	}
	
/*	public static void main(String[] args) {
		TGSongLoader l = new TGSongLoader();
		try {
			TGSong song = PrateekAPI.loadSong("C:/Documents and Settings/Prateek Tandon/Desktop/sunshine_of_your_love_ver2.gp3");
			System.out.println("THE SONG " + song.getName());
			TGTrack trak = song.getTrack(0);
			TGMeasure m = trak.getMeasure(0);
			List beats = m.getBeats();
			for(int x=0; x < beats.size(); x++) {
				TGBeat beat = (TGBeat) beats.get(x);
				System.out.println("BEAT " + x);
				for(int y=0; y < beat.countVoices(); y++) {
					TGVoice voice = beat.getVoice(y);
					List notes  = voice.getNotes();
					for(int z=0; z < notes.size(); z++) {
						TGNote n = (TGNote) notes.get(z);
						System.out.println(n.getString() + " " +  n.getValue());
					}
				}
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}*/

}
