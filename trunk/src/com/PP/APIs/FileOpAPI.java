package com.PP.APIs;

import java.io.File;

public class FileOpAPI {
	
	//global storage path
	public static final String SAVE_PATH_DIR = "data/";
	
	//temporary files created for playback
	public static final String TEMP_GP4 = "tmp.gp4";
	public static final String TEMP_MID = "tmp.mid";
	
	//model files
	public static final String PREFERENCE_FILE = "pref.dat";
	
	/**
	 * Called to init directory structure.
	 */
	public static void init() {
		File f = new File(SAVE_PATH_DIR);
		if(!f.exists()) {
			boolean created = f.mkdirs();
			System.out.println("Created " + SAVE_PATH_DIR);
		}
	}
}