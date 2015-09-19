package net.simsa.codeanalyzer.analyzers.directory;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.java.truevfs.access.TFile;
import net.simsa.codeanalyzer.analyzers.Analyzer;
import net.simsa.codeanalyzer.analyzers.AnalyzerFactory;
import net.simsa.codeanalyzer.analyzers.BatchEntityPersister;
import net.simsa.codeanalyzer.model.DebugStats;

public class DirectoryWalker implements Analyzer {
    static Logger log = LogManager.getLogger();

    TFile directory;
    AnalyzerFactory analyzers;
    
    BatchEntityPersister batch;

    public DirectoryWalker(AnalyzerFactory analyzers, BatchEntityPersister batch) {
	this.analyzers = analyzers;
	this.batch = batch;
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
	    log.debug("Located " + file.getCanonicalPath());
	    
	    DebugStats.record(analyzers.getFileExtension(file));
	    if (DebugStats.shouldEarlyExit()) { return; }
	    
	    Analyzer analyzer = analyzers.get(analyzers.getFileExtension(file), file.isDirectory());
	    analyzer.setSource(file);
	    analyzer.process();
	}

    }
    
}
