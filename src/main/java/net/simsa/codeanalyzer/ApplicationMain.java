package net.simsa.codeanalyzer;

import java.util.List;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import net.java.truevfs.access.TFile;
import net.simsa.codeanalyzer.analyzers.Analyzer;
import net.simsa.codeanalyzer.analyzers.AnalyzerFactory;
import net.simsa.codeanalyzer.analyzers.BatchEntityPersister;
import net.simsa.codeanalyzer.analyzers.directory.DirectoryWalker;
import net.simsa.codeanalyzer.model.DebugStats;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.weld.environment.se.bindings.Parameters;
import org.jboss.weld.environment.se.events.ContainerInitialized;

/**
 * Destination entry point after CDI initializes, and the application's main
 * logic should run.
 * 
 * Injection and hibernate are working, including JClass entity persistence.
 * Next, need to find a way to stream my object updates to hibernate instead of
 * hanging on until the entire tree recursion is finished.
 * 
 * @author jenny
 * 
 */
public class ApplicationMain {
    Logger log = LogManager.getLogger();

    String datadirPath;
    TFile datadir;

    @Inject
    BatchEntityPersister batch;

    public ApplicationMain() {
    }

    public void onStartup(@Observes ContainerInitialized event, @Parameters List<String> parameters) {
	this.datadirPath = Main.getParameters()[0];
	this.datadir = new TFile(datadirPath);

	try {
	    run();
	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

    public void run() throws Exception {
	log.warn("Beginning");
	if (!datadir.isDirectory()) {
	    throw new IllegalArgumentException("Data dir must be a valid directory path.");
	}
	if (datadir.listFiles().length < 1) {
	    throw new IllegalArgumentException("There are no files in the directory to process.");
	}

	Analyzer dirWalker = new AnalyzerFactory(batch).getDirectoryWalker();
	dirWalker.setSource(datadir);
	try {
	    dirWalker.process();
	    batch.completeFinalSave();
	} finally {
	    DebugStats.display();
	}

    }

}
