package com.PP.IntelliSeg.RepetionSegmenter.CrochemoreSegmenter.base;

import com.PP.APIs.FileOpAPI;
import com.PP.APIs.PlaybackEngineAPI;
import com.PP.IntelliSeg.Abstract.Segment;
import com.PP.LunarTabsDesktop.gui.DataModel;
import com.PP.PreferenceManager.PreferenceManager;

public class CrochemoreSegment extends Segment {

	/**
	 * Instantiate
	 * @param start
	 * @param end
	 */
	public CrochemoreSegment(int start, int end) {
		super(start, end);
	}
	
	@Override
	public void play() {
		DataModel dataModel = DataModel.getInstance();
        String tempo_str = PreferenceManager.getInstance().getString("playback_speed_pref", "1.00");
        double tempoScale = Double.parseDouble(tempo_str);
//		TuxGuitarUtil.playClip_beats(dataModel.getFilePath(), FileOpAPI.SAVE_PATH, getStart(),getEnd(),dataModel.getTrackNum());
		PlaybackEngineAPI.playClip(dataModel.getSong(), FileOpAPI.SAVE_PATH_DIR, getStart(),getEnd(),dataModel.getTrackNum(), tempoScale);				
	}
	
	@Override
	public String getTitlePresentation() {
		return "(Measure " + (getStart()+1) + "-"+ (getEnd()+1) + ")";
	}

}
