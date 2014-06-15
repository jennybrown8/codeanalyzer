package net.simsa.codeanalyzer.analyzers;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import net.simsa.codeanalyzer.analyzers.directory.DirectoryWalker;
import net.simsa.codeanalyzer.analyzers.directory.ZipFileWalker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AnalyzerFactory {
    Logger log = LogManager.getLogger();

    static String NULLEXTENSION = "NoExtension";
    
    @Inject
    EntityManager em;

    public AnalyzerFactory() {
    }
    
    @PostConstruct
    public void onPostConstruct() {
	em.getTransaction().begin();
    }
    
    public void commit() {
	em.getTransaction().commit();
    }
    
    @PreDestroy
    public void onPreDestroy() {
	em.close();
    }

    public Analyzer get(String extension, boolean isDirectory) {
	if (isDirectory) {
	    return new DirectoryWalker(this, em);
	} else if (extension.equals(NULLEXTENSION)) {
	    return new NoOpAnalyzer(this, em);
	} else if (extension.equals("zip") || extension.equals("war") || extension.equals("jar")) {
	    return new ZipFileWalker(this, em);
	} else if (extension.equals("class")) {
	    return new ClassAnalyzer(this, em);
	}
	return new NoOpAnalyzer(this, em);
    }

    public String getFileExtension(File file) throws IOException {
	return getFileExtension(file.getAbsolutePath());
    }

    public String getFileExtension(String path) throws IOException {
	String extension = null;
	if (path.indexOf(".") != -1) {
	    extension = path.substring(path.lastIndexOf(".") + 1);
	    if (extension.length() > 8) {
		extension = NULLEXTENSION;
	    }
	} else {
	    extension = NULLEXTENSION;
	}
	return extension;
    }

}
