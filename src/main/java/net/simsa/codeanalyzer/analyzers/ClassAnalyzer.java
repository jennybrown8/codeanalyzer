package net.simsa.codeanalyzer.analyzers;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import net.java.truevfs.access.TFile;
import net.java.truevfs.access.TFileInputStream;
import net.simsa.codeanalyzer.model.JClassImplementsJInterface;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;

public class ClassAnalyzer implements Analyzer {
    Logger log = LogManager.getLogger();

    private TFile file;

    AnalyzerFactory factory;
    BatchEntityPersister batch;

    public ClassAnalyzer(AnalyzerFactory factory, BatchEntityPersister batch) {
	this.factory = factory;
	this.batch = batch;
    }

    public void setSource(TFile file) throws IOException {
	this.file = file;
    }

    public String getIdentity() {
	return "ClassAnalyzer";
    }

    public void process() throws IOException {
	ClassEntityVisitor cev = new ClassEntityVisitor();

	TFileInputStream stream = null;
	try {
	    log.debug("Processing class " + file.getAbsolutePath());
	    stream = new TFileInputStream(file);
	    cev.setFile(file);
	    ClassReader cr = new ClassReader(stream);
	    cr.accept(cev, 0);
	    batch.add(cev.getJClass());
	} finally {
	    if (stream != null) {
		stream.close();
	    }
	}
    }

}
