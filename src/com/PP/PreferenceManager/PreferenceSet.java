package com.PP.PreferenceManager;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PreferenceSet implements Serializable {
	
	protected Map<String, Double> doubles;
	protected Map<String, Integer> integers;
	protected Map<String,String> strings;
	
	public PreferenceSet() {
		doubles = new HashMap<String,Double>();
		integers = new HashMap<String,Integer>();
		strings = new HashMap<String,String>();
	}
}
