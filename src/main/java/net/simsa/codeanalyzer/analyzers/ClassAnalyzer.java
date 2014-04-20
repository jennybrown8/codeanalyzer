package net.simsa.codeanalyzer.analyzers;

import java.io.IOException;

import net.java.truevfs.access.TFile;
import net.java.truevfs.access.TFileInputStream;

import org.objectweb.asm.ClassReader;

public class ClassAnalyzer implements Analyzer {

    private TFile file;

    public ClassAnalyzer() {
    }

    public void setSource(TFile file) throws IOException {
	this.file = file;
    }

    public String getIdentity() {
	return "ClassAnalyzer";
    }

    public void process() throws IOException {
	TFileInputStream stream = null;
	try {
	    System.out.println("Absolute path " + file.getAbsolutePath());
	    stream = new TFileInputStream(file);
	    ClassPrinter cp = new ClassPrinter();
	    ClassReader cr = new ClassReader(stream);
	    cr.accept(cp, 0);
	} finally {
	    if (stream != null) {
		stream.close();
	    }
	}
    }

    public static void main(String[] args) throws Exception {
	// for testing purposes.
	TFile file = new TFile(
		"C:\\Users\\jenny\\workspace\\analyze\\analyzer\\target\\classes\\net\\simsa\\codeanalyzer\\analyzers\\DummyTest.class");
	ClassAnalyzer analyzer = new ClassAnalyzer();
	analyzer.setSource(file);
	analyzer.process();
    }

}
