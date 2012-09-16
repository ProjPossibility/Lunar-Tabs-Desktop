package tg_import.io.ptb.helper;

import tg_import.io.ptb.base.PTTrackInfo;


public class TrackHelper {
	
	private TrackInfoHelper infoHelper;
	private TrackStartHelper startHelper;
	
	public TrackHelper(){
		this.infoHelper = new TrackInfoHelper();
		this.startHelper = new TrackStartHelper();
	}
	
	public void reset(PTTrackInfo defaultInfo){
		this.infoHelper.reset(defaultInfo);
	}
	
	public TrackInfoHelper getInfoHelper() {
		return this.infoHelper;
	}
	
	public TrackStartHelper getStartHelper() {
		return this.startHelper;
	}
}
