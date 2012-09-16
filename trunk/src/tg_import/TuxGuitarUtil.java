package tg_import;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import tg_import.io.base.TGSongLoader;
import tg_import.io.gtp.GP4OutputStream;
import tg_import.io.gtp.GTPSettings;
import tg_import.io.midi.MidiSongExporter;
import tg_import.song.factory.TGFactory;
import tg_import.song.models.TGBeat;
import tg_import.song.models.TGMeasure;
import tg_import.song.models.TGMeasureHeader;
import tg_import.song.models.TGNote;
import tg_import.song.models.TGSong;
import tg_import.song.models.TGTrack;
import tg_import.song.models.TGVoice;

import java.util.*;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

public class TuxGuitarUtil {
		
	public static void playClip(String file, int m0, int mf,int track) {
		try {
			TGSong song = TuxGuitarUtil.loadSong(file);
			TGSong newSong = TuxGuitarUtil.extractMeasures(song, track,m0, mf);
			TuxGuitarUtil.exportToGP4("test.gp4", newSong);		
			TGSong s3 = TuxGuitarUtil.loadSong("test.gp4");			
			TuxGuitarUtil.exportToMidi("test.mid", s3);
			
			Sequence sequence = MidiSystem.getSequence(new File("test.mid"));
			
			// Create a sequencer for the sequence
	        Sequencer sequencer = MidiSystem.getSequencer();
	        sequencer.open();
	        sequencer.setSequence(sequence);
	        
	//        Start playing
	        sequencer.start();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void exportToGP4(String file, TGSong song) {
		GP4OutputStream g = new GP4OutputStream(new GTPSettings());
		try {
			g.init(new TGFactory(), new BufferedOutputStream(new FileOutputStream(file)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g.writeSong(song);
	}
	
	public static TGSong extractMeasures(TGSong originalSong, int track, int m0, int mf) {
		TGTrack trackObj = originalSong.getTrack(track);
		List<TGMeasure> relMeasures = new ArrayList<TGMeasure>();
		List<TGMeasureHeader> relHeaders = new ArrayList<TGMeasureHeader>();
		for(int x=m0; x <= mf; x++) {
			relMeasures.add(trackObj.getMeasure(x));
			relHeaders.add(originalSong.getMeasureHeader(x));
		}
				
		//create new TGSong
		TGSong song = isolateTrack(originalSong, track);
		song.getTrack(0).clear();
		song.clearMeasureHeaders();
		for(int x=0; x < relMeasures.size(); x++) {
			TGMeasure m = relMeasures.get(x);
			TGMeasureHeader h = relHeaders.get(x);
			song.addMeasureHeader(h);
			song.getTrack(0).addMeasure(m);
		}
				
		return song;
	}

	public static TGSong isolateTrack(TGSong song, int t) {
		TGSong rtn = song.clone(new TGFactory());
		rtn.clearTracks();
		TGTrack track = song.getTrack(t);
		rtn.addTrack(track);
		return rtn;
	}
		
	public static TGSong loadSong(String file) throws Exception{
		TGSongLoader l = new TGSongLoader();
		return l.load(new TGFactory(), new FileInputStream(file));
	}
	
	public static void exportToMidi(String file, TGSong song) {
		MidiSongExporter e = new MidiSongExporter();
		try {
			e.exportSong(new BufferedOutputStream(new FileOutputStream(file)), song);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
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
	public static void main(String[] args) {
		try {
			TGSong song = TuxGuitarUtil.loadSong("melody_segmenter_matlab/sampleTabs/breakfast_at_tiffanys.gp3");
			TGSong newSong = TuxGuitarUtil.extractMeasures(song, 0,1, 25);
			TuxGuitarUtil.exportToGP4("test.gp4", newSong);		
			TGSong s3 = TuxGuitarUtil.loadSong("test.gp4");			
			TuxGuitarUtil.exportToMidi("test.mid", s3);
			
			Sequence sequence = MidiSystem.getSequence(new File("test.mid"));
			
			// Create a sequencer for the sequence
	        Sequencer sequencer = MidiSystem.getSequencer();
	        sequencer.open();
	        sequencer.setSequence(sequence);
	        
//	        Start playing
	        sequencer.start();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}
