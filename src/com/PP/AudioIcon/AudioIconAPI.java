package com.PP.AudioIcon;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.herac.tuxguitar.song.models.TGBeat;
import org.herac.tuxguitar.song.models.TGMeasure;
import org.herac.tuxguitar.song.models.TGNote;
import org.herac.tuxguitar.song.models.TGSong;
import org.herac.tuxguitar.song.models.TGTrack;
import org.herac.tuxguitar.song.models.TGVoice;
import com.PP.APIs.FileOpAPI;
import com.PP.APIs.PlaybackEngineAPI;
import com.PP.APIs.TuxGuitarUtil;

public class AudioIconAPI {
	
	//const
	public static final String TEMPLATE_FILE = "cons_template.gp3";
		
	//singleton
	protected AudioIconAPI() {}
	protected static AudioIconAPI instance = null;
	public static AudioIconAPI getInstance() {
		if(instance==null) {
			instance = new AudioIconAPI();
		}
		return instance;
	}
	
	/**
	 * Load Template File
	 * @return
	 */
	protected TGSong loadTemplate() {
		TGSong song = TuxGuitarUtil.loadSong(FileOpAPI.SAVE_PATH_DIR + TEMPLATE_FILE);	
		return song;
	}
	
	/**
	 * Helper for template beats
	 * @param song
	 * @return
	 */
	protected List<TGBeat> getTemplateBeatsHelper(TGSong song) {
		List<TGBeat> rtn = new ArrayList<TGBeat>();
		TGTrack track = song.getTrack(0);
		for(int x=0; x < track.countMeasures(); x++) {
			TGMeasure measure = track.getMeasure(x);
			for(int y=0; y < measure.countBeats(); y++) {
				rtn.add(measure.getBeat(y));
			}
		}
		return rtn;
	}
	
	protected TGSong createBeatAudioIconSong(TGBeat beat) {				
		if(beat.isRestBeat()) {
			return null;
		}
		else {
			
			//load template
			TGSong template = loadTemplate();
			List<TGBeat> templateBeats  = getTemplateBeatsHelper(template);
			
			//iterate through notes in order and add template beats
			int templateBeatCtr=0;
			for(int x=(beat.countVoices()-1); x  >= 0; x--) {
				TGVoice voice = beat.getVoice(x);
				for(int y=(voice.countNotes()-1); y >= 0; y--) {
					TGNote note = voice.getNote(y);
					TGBeat templateBeat = templateBeats.get(templateBeatCtr);
					templateBeat.getVoice(0).getNote(0).setString(note.getString());
					templateBeat.getVoice(0).getNote(0).setValue(note.getValue());
					templateBeatCtr++;
				}
			}
			
			//zero out all other beats in template beats
			for(int x=templateBeatCtr; x < templateBeats.size(); x++) {
				TGBeat templateBeat = templateBeats.get(x);
				templateBeat.clearVoices();
			}
			
			//return
			return template;
		}
	}
	
	public void playBeatAudioIcon(TGBeat beat) {
		
		try {
			//create song
			TGSong newSong = createBeatAudioIconSong(beat);
			
			//avoid rest-only measures
			if(newSong!=null) {
			
				//write MIDI file for selection
				TuxGuitarUtil.exportToGP4(FileOpAPI.SAVE_PATH_DIR + FileOpAPI.TEMP_GP4, newSong);		
				TGSong s3 = TuxGuitarUtil.loadSong(new FileInputStream(FileOpAPI.SAVE_PATH_DIR + FileOpAPI.TEMP_GP4));			
				TuxGuitarUtil.exportToMidi(FileOpAPI.SAVE_PATH_DIR + FileOpAPI.TEMP_MID, s3);
				
				//play
				PlaybackEngineAPI.play(new File(FileOpAPI.SAVE_PATH_DIR + FileOpAPI.TEMP_MID));

			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
