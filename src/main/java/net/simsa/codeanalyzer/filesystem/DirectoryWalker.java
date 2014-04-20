package net.simsa.codeanalyzer.filesystem;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import net.java.truevfs.access.TFile;
import net.simsa.codeanalyzer.analyzers.Analyzer;
import net.simsa.codeanalyzer.analyzers.AnalyzerFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DirectoryWalker implements Analyzer {
    static Logger log = LogManager.getLogger();

    AnalyzerFactory analyzers;
    TFile directory;

    public DirectoryWalker(TFile directory) {
	this.directory = directory;
	this.analyzers = new AnalyzerFactory();
    }

    public void setSource(TFile file) throws IOException {
	directory = file;
    }

    public String getIdentity() {
	return "DirectoryWalker";
    }

    /**
     * For the specified directory, iterates the files, and makes recursive call
     * for any directories
     */
    public void process() throws IOException {
	TFile[] files = directory.listFiles();
	for (TFile file : files) {
	    log.debug("Directory " + file);
	    if (file.isDirectory()) {
		DirectoryWalker walker = new DirectoryWalker(file);
		walker.process();
	    } else {
		log.debug("Located " + file.getCanonicalPath());

		Analyzer analyzer = analyzers.get(analyzers.getFileExtension(file));
		analyzer.setSource(file);
		analyzer.process();
	    }
	}

    }

}
