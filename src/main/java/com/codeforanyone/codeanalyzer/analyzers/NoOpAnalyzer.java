package com.codeforanyone.codeanalyzer.analyzers;

import java.io.IOException;
import java.io.InputStream;

import net.java.truevfs.access.TFile;

/** Does nothing with the file; silent skip. */
public class NoOpAnalyzer implements Analyzer {

    public NoOpAnalyzer(AnalyzerFactory factory, BatchEntityPersister batch) {

    }

    public boolean accepts(String path) {
	return true;
    }

    public void setSource(TFile file) {
    }

    public void setSource(InputStream stream) {

    }

    public String getIdentity() {
	return "SkipAnalyzer";
    }

    public void process() throws IOException {
    }

}
