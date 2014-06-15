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
 * Wrapper entry point to enable CDI initialization 
 */
public class Main extends StartMain {

    public Main(String[] commandLineArgs) {
	super(commandLineArgs);
    }

    public void init(String[] args) {
	super.main(args);
    }

    public static void main(String[] args) {
	if (args.length < 1) {
	    System.out.println("Usage: net.simsa.codeanalyzer.Main path/to/directory/with/war");
	} else {
	    Main me = new Main(args);
	    me.init(args);
	    /*
	     * <properties> <downloadSources>true</downloadSources>
	     * <downloadJavadocs>true</downloadJavadocs> </properties>
	     */

	}
    }
}
