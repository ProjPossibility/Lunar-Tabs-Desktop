package com.PP.IntelliSeg.MeasureIncrementSegmenter;

import com.PP.APIs.FileOpAPI;
import com.PP.APIs.PlaybackEngineAPI;
import com.PP.IntelliSeg.Abstract.Segment;
import com.PP.LunarTabsDesktop.gui.DataModel;
import com.PP.PreferenceManager.PreferenceManager;

public class MeasureIncrementSegment extends Segment {

	/**
	 * Ctr
	 * @param start
	 * @param end
	 */
	public MeasureIncrementSegment(int start, int end) {
		super(start, end);
	}

	@Override
	public void play() {
		DataModel dataModel = DataModel.getInstance();
        String tempo_str = PreferenceManager.getInstance().getString("playback_speed_pref", "1.00");
        double tempoScale = Double.parseDouble(tempo_str);
        if(dataModel.getPlaybackTrackInds()!=null) {
        	PlaybackEngineAPI.playClip_multiTrack(dataModel.getSong(), FileOpAPI.SAVE_PATH_DIR, getStart(),getEnd(),dataModel.getPlaybackTrackInds(), tempoScale);		
        }
        else {
        	PlaybackEngineAPI.playClip(dataModel.getSong(), FileOpAPI.SAVE_PATH_DIR, getStart(),getEnd(),dataModel.getTrackNum(), tempoScale);		
        }
	}

	@Override
	public String getTitlePresentation() {
		return "(Measure " + (getStart()+1) + "-" + (getEnd()+1) + ")";
	}
}
