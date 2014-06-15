package net.simsa.codeanalyzer.analyzers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import net.java.truevfs.access.TFile;

/** Does nothing with the file; silent skip. */
public class NoOpAnalyzer implements Analyzer {

	public boolean accepts(String path) {
		return true;
	}
	public List<Object> getEntities() {
	    return new ArrayList<Object>();
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
