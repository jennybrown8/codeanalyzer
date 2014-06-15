package net.simsa.codeanalyzer.analyzers;

import java.io.File;
import java.io.IOException;

import net.simsa.codeanalyzer.analyzers.directory.DirectoryWalker;
import net.simsa.codeanalyzer.analyzers.directory.ZipFileWalker;
import net.simsa.codeanalyzer.model.DebugStats;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AnalyzerFactory {
    Logger log = LogManager.getLogger();

    static String NULLEXTENSION = "NoExtension";
    static int filesProcessed = 0;

    public AnalyzerFactory() {
    }

    public Analyzer get(String extension, boolean isDirectory) {
	if (isDirectory) {
	    return new DirectoryWalker();
	} else if (extension.equals(NULLEXTENSION)) {
	    return new NoOpAnalyzer();
	} else if (extension.equals("zip") || extension.equals("war") || extension.equals("jar")) {
	    return new ZipFileWalker();
	} else if (extension.equals("class")) {
	    return new ClassAnalyzer();
	}
	return new NoOpAnalyzer();
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
