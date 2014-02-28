package com.PP.IntelliSeg.RepetionSegmenter.SMRSegmenter.base;

import java.util.Map;
import java.util.Set;
import com.PP.APIs.FileOpAPI;
import com.PP.APIs.PlaybackEngineAPI;
import com.PP.IntelliSeg.Abstract.Segment;
import com.PP.LunarTabsDesktop.gui.DataModel;
import com.PP.PreferenceManager.PreferenceManager;

public class SMRSegment extends Segment {
	
	/**
	 * Start set for segment 
	 */
	protected Set<Integer> startSet;
	
	/**
	 * Computed total repeat count.
	 */
	protected int totalRepeatCount=0;
	
	/**
	 * Instantiate
	 * @param start
	 * @param end
	 */
	public SMRSegment(int start, int end, Set<Integer> startSet) {
		super(start, end);
		this.startSet = startSet;
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
		/*
		StringBuffer rtn = new StringBuffer();
		rtn.append("M (");
		List<Integer> list = new ArrayList<Integer>(startSet);
		Collections.sort(list);
		for(int occ : list) {
			rtn.append(occ);
			rtn.append(",");
		}
		rtn.deleteCharAt(rtn.length()-1);
		rtn.append(")");
		return rtn.toString();
		*/
		return "(Measure " + (this.getStart()+1) + " x" + totalRepeatCount + ")";
	}
	
	/**
	 * Computes total repeat count based on both explicit and implicit repeats.
	 * @param repeats
	 */
	public void computeTotalRepeatCount(Map<Integer,Integer> repeats) {
		totalRepeatCount = startSet.size();
		for(int start : startSet) {
			if(repeats.containsKey((start+1))) {
				totalRepeatCount = totalRepeatCount + repeats.get((start+1));
			}
		}
	}
}
