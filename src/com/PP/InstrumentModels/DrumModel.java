package com.PP.InstrumentModels;

import com.PP.APIs.ResourceModel;


public class DrumModel {
		
	/**
	 * Singleton instance
	 */
	private static String[] instance = null; 
	
	public static String[] getDrumModel() {
		if(instance==null) {
			instance = new String[88];
			for(int x=27; x < (27+ResourceModel.DRUMS.length); x++) {
				instance[x] = ResourceModel.DRUMS[x-27];
			}					
		}
		return instance;		
	}
	

	public static String getDrumName(int index) {
		if(index >= 27 && index <= 87)
			return DrumModel.getDrumModel()[index];
		else {
			return ResourceModel.UNKNOWN_DRUM;
		}
			
	}
}
