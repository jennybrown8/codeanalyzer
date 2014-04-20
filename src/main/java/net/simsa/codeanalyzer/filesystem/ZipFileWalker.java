package net.simsa.codeanalyzer.filesystem;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import net.java.truevfs.access.TFile;
import net.simsa.codeanalyzer.analyzers.Analyzer;
import net.simsa.codeanalyzer.analyzers.AnalyzerFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ZipFileWalker implements Analyzer {
    static Logger log = LogManager.getLogger();

    AnalyzerFactory analyzers;

    File file;

    public ZipFileWalker() {
	analyzers = new AnalyzerFactory();
    }

    public void setSource(TFile file) throws IOException {
	this.file = file;
    }

    public String getIdentity() {
	return "ZipFileWalker";
    }

    public void process() throws IOException {
	log.info("Process zip file using TFile");
	TFile zipfile = new TFile(file);
	TFile[] files = zipfile.listFiles();

	for (TFile file : files) {
	    log.info("ZipFile entry isDirectory = " + file.isDirectory() + ", name=" + file.getAbsolutePath());

	    Analyzer analyzer = null;
	    if (file.isDirectory()) {
		analyzer = new DirectoryWalker(file);
	    } else {
		analyzer = analyzers.get(analyzers.getFileExtension(file.getName()));
		analyzer.setSource(file);
	    }
	    analyzer.process();
	}

    }

}
