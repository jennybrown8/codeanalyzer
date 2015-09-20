package net.simsa.codeanalyzer.texturegen;

import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class DiagramImageFactory {

    public DiagramImageFactory() throws IOException {
	System.setProperty("java.awt.headless", "true");
	Toolkit tk = Toolkit.getDefaultToolkit();
	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	boolean headless_check = ge.isHeadless();
	// System.out.println("Is headless? " + headless_check);

	File outputDir = new File("target/images");
	if (!outputDir.exists()) {
	    boolean made = outputDir.mkdir();
	    if (!made) {
		throw new RuntimeException("Could not mkdir target/images/ - check permissions.");
	    }
	}
	if (!outputDir.canWrite()) {
	    throw new RuntimeException(
		    "Cannot write to output dir " + outputDir.getCanonicalPath() + " - check permissions.");
	}
    }

    public void generate(String packageName, String className, List<String> methods) {
	ClassDiagramBox box = new ClassDiagramBox(packageName, className);
	for (String method : methods) {
	    box.add(method);
	}
	box.saveCanvas("target/images/" + box.getSuggestedFilename());
    }

    private void createTestPic() {
	String[] methds = { "a", "getFirstMethod()", "setFirstMethod()", "getSecond()", "setSecond()",
		"aReallyLongMethodToTestWidth()" };
	List<String> methods = Arrays.asList(methds);
	generate("net.simsa.mypackage", "TestClass", methods);
    }

    public static void main(String[] args) throws Exception {
	DiagramImageFactory dif = new DiagramImageFactory();
	dif.createTestPic();
	System.out.println("Done");
    }

}
