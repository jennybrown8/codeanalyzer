package com.codeforanyone.codeanalyzer.model;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DebugStats {
    static Logger log = LogManager.getLogger();

    public static int debugEarlyExit = 1000 * 30;
    public static int filesProcessed = 0;

    static Map<String, Integer> counts = new HashMap<String, Integer>();

    public static void record(String extension) {
	Integer count = counts.get(extension);
	if (count == null) {
	    count = 0;
	}
	counts.put(extension, ++count);

	filesProcessed++;
	if (filesProcessed % 100 == 0) {
	    log.info("Processed " + filesProcessed + " files.");
	}
    }

    public static boolean shouldEarlyExit() {
	boolean earlyExit = (DebugStats.debugEarlyExit != -1) && (filesProcessed > debugEarlyExit);
	if (earlyExit) {
	    log.info("Exiting early due to debug turned on.");
	}
	return earlyExit;
    }

    public static void display() {
	for (String key : counts.keySet()) {
	    log.info(key + "\t" + counts.get(key));
	}

    }

}
