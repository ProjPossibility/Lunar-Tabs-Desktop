package com.PP.IntelliSeg.MarkerSegmenter;
import com.PP.APIs.FileOpAPI;
import com.PP.APIs.PlaybackEngineAPI;
import com.PP.IntelliSeg.Abstract.Segment;
import com.PP.LunarTabsDesktop.gui.DataModel;

public class MarkerSegment extends Segment {

	//marker Text
	protected String markerText;
	
	public MarkerSegment(int start, int end, String markerText) {
		super(start, end);
		this.markerText = markerText;
	}

	@Override
	public void play() {
		DataModel dataModel = DataModel.getInstance();
		String tempo_str = com.PP.PreferenceManager.PreferenceManager.getInstance().getString("playback_speed_pref", "1.00");
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
		if(markerText!=null && !markerText.equals("")) {
			return "(" + markerText + ")";	
		}
		else {
			return "(Measure" + (getStart()+1) + "-" + (getEnd()+1) + ")";
		}
	}
}