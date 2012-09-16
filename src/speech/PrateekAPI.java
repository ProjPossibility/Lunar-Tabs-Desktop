package speech;
import java.io.FileInputStream;

import tg_import.io.base.*;
import tg_import.song.factory.TGFactory;
import tg_import.song.models.*;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class PrateekAPI {
	
	private static VoiceManager voiceManager = VoiceManager.getInstance();
	private static Voice kevin;
	private static boolean voiceInit = false;
	
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
	
	public static void say(String str) {
		if(!voiceInit) {
			kevin = voiceManager.getVoice("kevin16");
			kevin.allocate();
			kevin.setStyle("causual");
			kevin.setVerbose(false);
			kevin.setRate(150*2);
			voiceInit = true;
		}
		kevin.speak(str);
	}
}
