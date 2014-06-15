package net.simsa.codeanalyzer.analyzers;

import java.io.IOException;

import net.java.truevfs.access.TFile;
import net.java.truevfs.access.TFileInputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;

public class ClassAnalyzer implements Analyzer {
    Logger log = LogManager.getLogger();

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
	    log.debug("Processing class " + file.getAbsolutePath());
	    stream = new TFileInputStream(file);
	    ClassEntityVisitor cp = new ClassEntityVisitor(file);
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
	TFile file = new TFile(args[0]);
	if(!file.exists()) {
	    throw new IllegalArgumentException("File not found: " + file.getCanonicalPath());
	}
	ClassAnalyzer analyzer = new ClassAnalyzer();
	analyzer.setSource(file);
	analyzer.process();
    }

}
