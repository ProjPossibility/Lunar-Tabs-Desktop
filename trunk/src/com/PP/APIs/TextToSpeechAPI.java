package com.PP.APIs;
import java.io.FileInputStream;

import org.herac.tuxguitar.io.base.TGSongLoader;
import org.herac.tuxguitar.song.models.TGSong;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class TextToSpeechAPI {
	
	private static VoiceManager voiceManager = VoiceManager.getInstance();
	private static Voice kevin;
	private static boolean voiceInit = false;
	private static long lastSpeak = -1;
	public static final int LAST_SPEAK_TIMEOUT = 5000;
	public static final boolean USE_LAST_SPEAK_HACK = true;
	
	public static void speak(String str) {
		if(!voiceInit) {
			kevin = voiceManager.getVoice("kevin16");
			kevin.allocate();
			kevin.setStyle("causual");
			kevin.setVerbose(false);
			kevin.setRate(200);
			voiceInit = true;
		}
		if(!USE_LAST_SPEAK_HACK) {
			kevin.speak(str);
		}
		else {
			long cTime = System.currentTimeMillis();
			if(lastSpeak==-1 || (cTime-lastSpeak) > LAST_SPEAK_TIMEOUT) {
				lastSpeak = cTime;
				kevin.speak(str);
			}
		}
	}
}
