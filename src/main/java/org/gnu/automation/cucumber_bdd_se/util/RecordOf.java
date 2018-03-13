package org.gnu.automation.cucumber_bdd_se.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title:        RecordOf
 * 
 * Description:  RecordOf is an abstraction of a record  of
 * 
 * Author:       Josemar Silva - josemarsilva@inmetrics.com.br
 * 
 */
public class RecordOf {
	
//	final static Logger logger = Logger.getLogger(RecordOf.class);
	
	private Map<String, String> recordOfKeyValue;	
	private List<String> listOfKeys = null;
	
	/**
	 * RecordOf () - Construtor
	 */
	public RecordOf() {
		recordOfKeyValue = new HashMap<String,String>();
		listOfKeys = new ArrayList<String>();
	}
	
	/**
	 * get (key) - Get the value of a RecordOf key
	 * 
	 * @param key Key to get
	 * 
	 */
	public String get(String key) throws Exception {
		return recordOfKeyValue.get(key);
	}
	
	/**
	 * set (key,value) - Set the value of a RecordOf key
	 * 
	 * @param key   Key to be set
	 * @param value Value to be set
	 * 
	 */
	public void set(String key, String value) {
		recordOfKeyValue.put(key, value);
		listOfKeys.add(key);
	}
	
	
	/**
	 * remove(key) - Remove the key
	 * 
	 * @param key Key to be removed
	 * 
	 */
	public void remove(String key) {
		recordOfKeyValue.remove(key);
	}
	
	
	/**
	 * getListOfKeys - Return List of keys sort by add order
	 *  
	 * @return
	 * 
	 */
	public List<String> getListOfKeys() {
		return listOfKeys;
	}

}
