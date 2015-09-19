package net.simsa.codeanalyzer.analyzers;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.simsa.codeanalyzer.analyzers.directory.DirectoryWalker;
import net.simsa.codeanalyzer.analyzers.directory.ZipFileWalker;

public class AnalyzerFactory {
    Logger log = LogManager.getLogger();

    public static String NULLEXTENSION = "NoExtension";

    BatchEntityPersister batch;

    public AnalyzerFactory(BatchEntityPersister batch) {
	this.batch = batch;
    }
    
    public DirectoryWalker getDirectoryWalker() {
	return new DirectoryWalker(this, batch);
    }

    public Analyzer get(String extension, boolean isDirectory) {
	if (isDirectory) {
	    return new DirectoryWalker(this, batch);
	} else if (extension.equals(NULLEXTENSION)) {
	    return new NoOpAnalyzer(this, batch);
	} else if (extension.equals("zip") || extension.equals("war") || extension.equals("jar")) {
	    return new ZipFileWalker(this, batch);
	} else if (extension.equals("class")) {
	    return new ClassAnalyzer(this, batch);
	}
	return new NoOpAnalyzer(this, batch);
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
