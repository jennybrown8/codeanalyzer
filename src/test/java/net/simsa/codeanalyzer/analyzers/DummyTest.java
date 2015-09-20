package net.simsa.codeanalyzer.analyzers;

import java.io.IOException;
import java.util.List;

import com.codeforanyone.codeanalyzer.analyzers.Analyzer;

import net.java.truevfs.access.TFile;

public class DummyTest implements Analyzer {

    public void setSource(TFile file) throws IOException {
    }

    public String getIdentity() {
	return null;
    }

    public void process() throws IOException {
    }

    private static class MyInner {
	String foo;
	public MyInner() {
	    
	}
    }

    public List<Object> getEntities() {
	return null;
    }
    
}
