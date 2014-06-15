package net.simsa.codeanalyzer.analyzers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.java.truevfs.access.TFile;
import net.java.truevfs.access.TFileInputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;

public class ClassAnalyzer implements Analyzer {
    Logger log = LogManager.getLogger();

    private TFile file;
    
    ClassEntityVisitor cev;

    public ClassAnalyzer() {
	cev = new ClassEntityVisitor();
    }

    public List<Object> getEntities() {
	List<Object> list = new ArrayList<Object>();
	list.addAll(cev.getEntities());
	return list;
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
	    cev.setFile(file);
	    ClassReader cr = new ClassReader(stream);
	    cr.accept(cev, 0);
	} finally {
	    if (stream != null) {
		stream.close();
	    }
	}
    }


}
