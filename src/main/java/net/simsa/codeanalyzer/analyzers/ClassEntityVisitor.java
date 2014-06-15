package net.simsa.codeanalyzer.analyzers;

import net.java.truevfs.access.TFile;
import net.simsa.codeanalyzer.model.JClass;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ClassEntityVisitor extends ClassVisitor {

    Logger log = LogManager.getLogger();
    
    JClass jclass;
    TFile fileVisiting;
    
    public ClassEntityVisitor() {
	super(Opcodes.ASM4);
	jclass = new JClass();
    }
    
    public void setFile(TFile file) {
	this.fileVisiting = file;
    }
    
    public JClass getJClass() {
	return jclass;
    }

    public void visit(int version, int access, String name, String signature, String superName,
	    String[] interfaces) {
	jclass.setFullyQualifiedName(name);
	jclass.setSimpleName(name.substring(name.lastIndexOf("/") + 1));
	jclass.setSuperclassName(superName);
	jclass.setPackageName(name.substring(0, name.lastIndexOf("/")));
	jclass.setOrganization(name.substring(0, name.indexOf("/", name.indexOf("/")+1)));
    }

    public void visitSource(String source, String debug) {
	jclass.setSourceFile(source);
	if (fileVisiting != null) {
	    jclass.setSourcePath(fileVisiting.getPath());
	}
    }

    public void visitOuterClass(String owner, String name, String desc) {
//	log.debug("   Outer class " + owner + " - " + name);
    }

    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
//	log.debug("   Annotation " + desc);
	return null;
    }

    public void visitAttribute(Attribute attr) {
//	log.debug("   Attribute " + attr.toString());
    }

    public void visitInnerClass(String name, String outerName, String innerName, int access) {
//	log.debug("   Inner class " + name + " , " + outerName + " , " + innerName);
    }

    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
//	log.debug("   Field " + desc + " " + name + " " + signature + " " + value);
	return null;
    }

    public MethodVisitor visitMethod(int access, String name, String desc, String signature,
	    String[] exceptions) {
//	log.debug("   Method " + name + desc);
	return null;
    }

    public void visitEnd() {
	log.debug("Visited " + jclass.getFullyQualifiedName());
    }
}
