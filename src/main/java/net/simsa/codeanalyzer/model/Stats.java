package net.simsa.codeanalyzer.model;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Stats {
	static Logger log = LogManager.getLogger();
	
	public static int debugEarlyExit = 200;

	static Map<String, Integer> counts = new HashMap<String, Integer>();
	
	public static void record(String extension) {
		Integer count = counts.get(extension);
		if (count == null) { count = 0; }
		counts.put(extension, ++count);
	}
	
	public static void display() {
		for (String key : counts.keySet()) {
			log.info(key + "\t" + counts.get(key));
		}

	}
	
}
