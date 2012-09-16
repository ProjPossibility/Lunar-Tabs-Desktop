package MelodySegmentation;

import java.util.*;

public class Loader {
	
	/**
	 * Extract all grams of a string
	 * @param str String to extract n-grams from
	 * @return map of n-grams
	 */
	public Map<String,Integer> getAllNGrams(String str) {
		Map<String,Integer> rtn = new HashMap<String,Integer>();
		for(int n=0; n < str.length(); n++) {
			for(int i=0; i < str.length(); i++) {
				String gram = str.substring(i,i+n-1);
				if(!rtn.containsKey((gram))) {
					rtn.put(gram, 1);
				}
				else {
					rtn.put(gram, rtn.get(gram) + 1);
				}
			}
		}
		return rtn;
	}
}
