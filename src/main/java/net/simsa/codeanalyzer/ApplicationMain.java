package net.simsa.codeanalyzer;

import java.util.Date;
import java.util.List;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import net.java.truevfs.access.TFile;
import net.simsa.codeanalyzer.filesystem.DirectoryWalker;
import net.simsa.codeanalyzer.model.DebugStats;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.weld.environment.se.StartMain;
import org.jboss.weld.environment.se.bindings.Parameters;
import org.jboss.weld.environment.se.events.ContainerInitialized;

/**
 * Destination entry point after CDI initializes, and the application's main logic should run.
 * 
 * Injection is working.  Hibernate is working.  I need to convert the entities to have
 * JPA annotations, delete the DAO manager class, and validate that all entities
 * save correctly in Hibernate instead.  Also along the way, decide on the batch size
 * for bulk updates, so I don't overload a commit transaction unnecessarily.
 * 
 * @author jenny
 *
 */
public class ApplicationMain {
    static Logger log = LogManager.getLogger();

    String datadirPath;
    TFile datadir;
    EntityManagerFactory entityManagerFactory;

    public ApplicationMain() {
    }

    public void onStartup(@Observes ContainerInitialized event, @Parameters List<String> parameters) {
	this.datadirPath = Main.getParameters()[0];
	this.datadir = new TFile(datadirPath);
	entityManagerFactory = Persistence.createEntityManagerFactory("net.simsa.codeanalyzer.PU");

//	runHibernate();
	//@formatter:off
	/*
	try {
	    EntityDAO.getManager();
	    run();
	} catch (Exception e) {
	    if (e.getMessage() != null && e.getMessage().indexOf("Early exit") != -1) {
		System.out.println(e.getMessage());
	    } else {
		e.printStackTrace();
	    }
	} finally {
	    EntityDAO.getManager().teardown();
	}
	*/
	//@formatter:on	
	
    }

    public void run() throws Exception {
	log.warn("Beginning");
	if (!datadir.isDirectory()) {
	    throw new IllegalArgumentException("Data dir must be a valid directory path.");
	}
	if (datadir.listFiles().length < 1) {
	    throw new IllegalArgumentException("There are no files in the directory to process.");
	}
	DirectoryWalker dirWalker = new DirectoryWalker(datadir);
	dirWalker.process();
	DebugStats.display();
    }

    // temporary testing code
    public void runHibernate() {
	EntityManager entityManager = entityManagerFactory.createEntityManager();
	entityManager.getTransaction().begin();
	entityManager.persist(new Event("Our very first event!", new Date()));
	entityManager.persist(new Event("A follow up event", new Date()));
	entityManager.getTransaction().commit();
	entityManager.close();

	entityManager = entityManagerFactory.createEntityManager();
	entityManager.getTransaction().begin();
	List<Event> result = entityManager.createQuery("from Event", Event.class).getResultList();
	for (Event event : result) {
	    System.out.println("Event (" + event.getDate() + ") : " + event.getTitle());
	}
	entityManager.getTransaction().commit();
	entityManager.close();
    }

}
