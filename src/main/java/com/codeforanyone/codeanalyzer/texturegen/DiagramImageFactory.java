package com.codeforanyone.codeanalyzer.texturegen;

import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class DiagramImageFactory {
    
    String outputDir;

    public DiagramImageFactory(String outputDir) {
	this.outputDir = outputDir;
	
	System.setProperty("java.awt.headless", "true");
	Toolkit tk = Toolkit.getDefaultToolkit();
	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	boolean headless_check = ge.isHeadless();
	// System.out.println("Is headless? " + headless_check);

	File outputDirF = new File(outputDir);
	String path = null;
	try {
	    path = outputDirF.getCanonicalPath();
	} catch (IOException io) {
	    throw new RuntimeException(io);
	}

	if (!outputDirF.exists()) {
	    boolean made = outputDirF.mkdir();
	    if (!made) {
		throw new RuntimeException("Could not mkdir target/images/ - check permissions.");
	    }
	}
	if (!outputDirF.canWrite()) {
	    throw new RuntimeException("Cannot write to output dir " + path + " - check permissions.");
	}
    }

    public void generate(String packageName, String className, SortedSet<String> methods) {
	ClassDiagramBox box = new ClassDiagramBox(packageName, className);
	for (String method : methods) {
	    box.add(method);
	}
	box.saveCanvas(outputDir);
    }

    private void createTestPic() {
	String[] methds = { "a", "getFirstMethod()", "setFirstMethod()", "getSecond()", "setSecond()",
		"aReallyLongMethodToTestWidth()" };
	SortedSet<String> methods = new TreeSet<String>();
	methods.addAll(Arrays.asList(methds));
	generate("net.simsa.mypackage", "TestClass", methods);
    }

    public static void main(String[] args) throws Exception {
	DiagramImageFactory dif = new DiagramImageFactory("target/test-images");
	dif.createTestPic();
	System.out.println("Done");
    }

}
