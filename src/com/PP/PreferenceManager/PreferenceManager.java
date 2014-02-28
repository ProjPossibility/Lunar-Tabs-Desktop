package com.PP.PreferenceManager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.PP.APIs.FileOpAPI;

public class PreferenceManager {
		
	//state
	protected PreferenceSet prefSet;
	
	//singleton
	protected PreferenceManager() {
		prefSet = load(FileOpAPI.SAVE_PATH_DIR + FileOpAPI.PREFERENCE_FILE);
		if(prefSet==null) {
			prefSet = new PreferenceSet();
		}
	}
	protected static PreferenceManager instance = null;
	public static PreferenceManager getInstance() {
		if(instance==null) {
			instance = new PreferenceManager();
		}
		return instance;
	}
	
	public PreferenceSet load(String filename) {
	    FileInputStream fis = null;
	    ObjectInputStream in = null;
	    PreferenceSet rtn = null;
	    try {
	      fis = new FileInputStream(filename);
	      in = new ObjectInputStream(fis);
	      rtn = (PreferenceSet) in.readObject();
	      in.close();
	    } 
	    catch (Exception ex) {
	      ex.printStackTrace();
	    }
	    return rtn;
	}
	
	public void save(String filename, PreferenceSet p) {
	    // save the object to file
	    FileOutputStream fos = null;
	    ObjectOutputStream out = null;
	    try {
	      fos = new FileOutputStream(filename);
	      out = new ObjectOutputStream(fos);
	      out.writeObject(p);
	      out.close();
	    } 
	    catch (Exception ex) {
	      ex.printStackTrace();
	    }
	}
	
	protected void quickSave() {
		save(FileOpAPI.SAVE_PATH_DIR + FileOpAPI.PREFERENCE_FILE,prefSet);
	}
	
	public void store(String key, String value) {
		prefSet.strings.put(key, value);
		quickSave();
	}
	
	public void store(String key, double value) {
		prefSet.doubles.put(key, value);
		quickSave();		
	}
	
	public void store(String key, int value) {
		prefSet.integers.put(key, value);
		quickSave();		
	}
	
	public String getString(String key, String defaultValue) {
		if(prefSet.strings.containsKey(key)) {
			return prefSet.strings.get(key);
		}
		else {
			return defaultValue;
		}
	}
	
	public double getDouble(String key, double defaultValue) {
		if(prefSet.doubles.containsKey(key)) {
			return prefSet.doubles.get(key);
		}
		else {
			return defaultValue;
		}
	}
	
	public int getInt(String key, int defaultValue) {
		if(prefSet.integers.containsKey(key)) {
			return prefSet.integers.get(key);
		}
		else {
			return defaultValue;
		}
	}
}
