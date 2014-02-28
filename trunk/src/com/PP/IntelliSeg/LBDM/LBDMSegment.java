package com.PP.IntelliSeg.LBDM;

import com.PP.APIs.FileOpAPI;
import com.PP.APIs.PlaybackEngineAPI;
import com.PP.IntelliSeg.Abstract.Segment;
import com.PP.LunarTabsDesktop.gui.DataModel;
import com.PP.PreferenceManager.PreferenceManager;

public class LBDMSegment extends Segment {

	/**
	 * Ctr
	 * @param start
	 * @param end
	 */
	public LBDMSegment(int start, int end) {
		super(start, end);
	}

	@Override
	public void play() {
		DataModel dataModel = DataModel.getInstance();
        String tempo_str = PreferenceManager.getInstance().getString("playback_speed_pref", "1.00");
        double tempoScale = Double.parseDouble(tempo_str);
		PlaybackEngineAPI.playClip_beats(dataModel.getSong(), FileOpAPI.SAVE_PATH_DIR, getStart(),getEnd(),dataModel.getTrackNum(), tempoScale);		
	}

	@Override
	public String getTitlePresentation() {
		return "(Measure " + (getStart()+1) + "-" + (getEnd()+1) + ")";
	}
	
}
