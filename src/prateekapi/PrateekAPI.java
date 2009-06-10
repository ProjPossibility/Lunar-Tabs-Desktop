package prateekapi;
import java.io.FileInputStream;

import org.herac.tuxguitar.song.factory.TGFactory;
import org.herac.tuxguitar.song.models.*;
import org.herac.tuxguitar.io.base.*;
import java.io.*;

public class PrateekAPI {
	
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
