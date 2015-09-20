package net.simsa.codeanalyzer.texturegen;

import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

public class CreateTestImage {

    private static void createPic() {
	ClassDiagramBox c = new ClassDiagramBox("net.simsa.mypackage", "TestClass");
	c.add("getFirstMethod()");
	c.add("setFirstMethod()");
	c.add("getSecondMethod()");
	c.add("setSecondMethod()");
	c.saveCanvas("target/foo.png");
    }

    public static void main(String[] args) throws Exception {
	System.setProperty("java.awt.headless", "true");
	Toolkit tk = Toolkit.getDefaultToolkit();
	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	boolean headless_check = ge.isHeadless();

	createPic();
	System.out.println("Done");
    }

}
