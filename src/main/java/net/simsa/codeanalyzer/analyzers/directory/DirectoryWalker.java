package net.simsa.codeanalyzer.analyzers.directory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.java.truevfs.access.TFile;
import net.simsa.codeanalyzer.analyzers.Analyzer;
import net.simsa.codeanalyzer.analyzers.AnalyzerFactory;
import net.simsa.codeanalyzer.model.DebugStats;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DirectoryWalker implements Analyzer {
    static Logger log = LogManager.getLogger();

    TFile directory;
    AnalyzerFactory analyzers;
    List<Object> list;


    public DirectoryWalker() {
	analyzers = new AnalyzerFactory();
	list = new ArrayList<Object>();
    }

    public void setSource(TFile file) throws IOException {
	directory = file;
    }

    public String getIdentity() {
	return "DirectoryWalker";
    }

    public List<Object> getEntities() {
	return list;
    }
    
    /**
     * For the specified directory, iterates the files, and makes recursive call
     * for any directories
     */
    public void process() throws IOException {
	TFile[] files = directory.listFiles();
	for (TFile file : files) {
	    log.info("Located " + file.getCanonicalPath());
	    
	    DebugStats.record(analyzers.getFileExtension(file));
	    if (DebugStats.shouldEarlyExit()) { return; }
	    
	    Analyzer analyzer = analyzers.get(analyzers.getFileExtension(file), file.isDirectory());
	    analyzer.setSource(file);
	    analyzer.process();
	    list.addAll(analyzer.getEntities());
	}

    }
    
}
