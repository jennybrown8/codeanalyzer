package net.simsa.codeanalyzer;

import java.io.File;

import net.java.truevfs.access.TFile;
import net.simsa.codeanalyzer.filesystem.DirectoryWalker;
import net.simsa.codeanalyzer.model.Stats;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Hello world!
 * 
 */
public class Main {
    static Logger log = LogManager.getLogger();

    String datadirPath;

    TFile datadir;

    public Main(String datadirPath) {
	this.datadirPath = datadirPath;
	this.datadir = new TFile(datadirPath);
    }

    public void run() throws Exception {
	log.warn("Beginning");
	if (!datadir.isDirectory()) {
	    throw new IllegalArgumentException("Data dir must be a valid directory path.");
	}
	if (datadir.listFiles().length < 1) {
	    throw new IllegalArgumentException("There are no files in the directory to process.");
	}
	DirectoryWalker dirWalker = new DirectoryWalker(datadir);
	dirWalker.process();
	Stats.display();
    }

    public static void main(String[] args) throws Exception {
	Main main = new Main(args[0]);
	main.run();
    }
}
