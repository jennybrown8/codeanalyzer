package net.simsa.codeanalyzer.analyzers;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import net.java.truevfs.access.TFile;
import net.java.truevfs.access.TFileInputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;

public class ClassAnalyzer implements Analyzer {
    Logger log = LogManager.getLogger();

    private TFile file;

    AnalyzerFactory factory;
    EntityManager em;

    public ClassAnalyzer(AnalyzerFactory factory, EntityManager em) {
	this.factory = factory;
	this.em = em;
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
	    em.persist(cev.getJClass());
	} finally {
	    if (stream != null) {
		stream.close();
	    }
	}
    }

}
