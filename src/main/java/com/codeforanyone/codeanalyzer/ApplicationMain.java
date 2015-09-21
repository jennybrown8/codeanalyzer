package com.codeforanyone.codeanalyzer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.weld.environment.se.bindings.Parameters;
import org.jboss.weld.environment.se.events.ContainerInitialized;

import com.codeforanyone.codeanalyzer.analyzers.Analyzer;
import com.codeforanyone.codeanalyzer.analyzers.AnalyzerFactory;
import com.codeforanyone.codeanalyzer.analyzers.BatchEntityPersister;
import com.codeforanyone.codeanalyzer.analyzers.RelationshipDbUpdater;
import com.codeforanyone.codeanalyzer.model.DebugStats;
import com.codeforanyone.codeanalyzer.model.JClass;
import com.codeforanyone.codeanalyzer.model.JMethod;
import com.codeforanyone.codeanalyzer.texturegen.DiagramImageFactory;

import net.java.truevfs.access.TFile;

/**
 * Destination entry point after CDI initializes, and the application's main
 * logic should run.
 * 
 * Injection and hibernate are working, including JClass entity persistence.
 * Next, need to find a way to stream my object updates to hibernate instead of
 * hanging on until the entire tree recursion is finished.
 * 
 * See run() method (very bottom) for overall processing pipeline control.
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

    @Inject
    RelationshipDbUpdater dbUpdater;

    @Inject
    EntityManager em;

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

    private void ingestSourceCodeToDb() throws IOException {
	if (!datadir.isDirectory()) {
	    throw new IllegalArgumentException("Data dir must be a valid directory path.");
	}
	if (datadir.listFiles().length < 1) {
	    throw new IllegalArgumentException("There are no files in the directory to process.");
	}

	Analyzer dirWalker = new AnalyzerFactory(batch).getDirectoryWalker();
	dirWalker.setSource(datadir);
	try {
	    // Clean the database in preparation.
	    dbUpdater.before();

	    // Walk every jar/war starting in this directory, creating entities
	    // as we go.
	    dirWalker.process();

	    // The batch entity saving may have an incomplete batch in the final
	    // set, so save those.
	    batch.completeFinalSave();

	    // Fill in relational information in bulk without overhead of entity
	    // traversal.
	    dbUpdater.after();
	} finally {
	    DebugStats.display();
	}
    }

    private void fromDbCreateClassDiagramImages() {
	DiagramImageFactory ifac = new DiagramImageFactory("target/images");
	Query q = em.createQuery("from JClass");
	List<JClass> classes = q.getResultList();
	int count = 0;
	for (JClass jclass : classes) {
	    List<String> methods = new ArrayList<String>();
	    Set<JMethod> jmethods = jclass.getJMethods();
	    for (JMethod method : jmethods) {
		methods.add(method.getMethodName());
	    }
	    
	    ifac.generate(jclass.getPackageName(), jclass.getSimpleName(), methods);
	    if (++count % 100 == 0) {
		System.out.println("Generated " + count + " images");
	    }
	}
    }

    public void run() throws Exception {
	log.warn("Beginning");
	ingestSourceCodeToDb();
	fromDbCreateClassDiagramImages();

    }

}
