package com.codeforanyone.codeanalyzer.analyzers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.codeforanyone.codeanalyzer.model.JClass;

import net.java.truevfs.access.TFile;

public class ClassEntityVisitor extends ClassVisitor  {

    static int classesVisited = 0;
    static final int DEBUG_ABORT_AFTER_N_CLASSES=30;
    
    Logger log = LogManager.getLogger();
    
    JClass jclass;
    TFile fileVisiting;
    
    public ClassEntityVisitor() {
	super(Opcodes.ASM4);
	jclass = new JClass();
	classesVisited++;
	if (classesVisited > DEBUG_ABORT_AFTER_N_CLASSES) {
	    throw new RuntimeException("Debug threshold reached, stopping.");
	}
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
	jclass.setInterfaces(interfaces);
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
	log.debug("   Attribute " + attr.toString());
    }

    public void visitInnerClass(String name, String outerName, String innerName, int access) {
//	log.debug("   Inner class " + name + " , " + outerName + " , " + innerName);
    }

    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
	// Field Ljava/lang/String; name null null
	// Field J serialVersionUID null 1
	// Field I value null null
	// Field Z immutable null null
	// Field Ljava/lang/Class; clazz Ljava/lang/Class<*>; null
	// Field Lorg/apache/wicket/util/visit/IVisitFilter; ANY null null
	// Field Lorg/apache/wicket/util/visit/Visit$Action; CONTINUE_BUT_DONT_GO_DEEPER null null
	// Field [Lorg/apache/wicket/util/visit/Visit$Action; $VALUES null null
	// Field Lorg/apache/wicket/util/time/Time; lastModifiedTime null null
	log.debug("   Field " + desc + " " + name + " " + signature + " " + value);
	return null;
    }

    public MethodVisitor visitMethod(int access, String name, String desc, String signature,
	    String[] exceptions) {
	// Method <init> (Ljava/lang/String;)V
	// Method locateInputSource ()Lorg/xml/sax/InputSource;
	// Method <init> (Ljava/lang/String;Lorg/apache/wicket/util/xml/CustomEntityResolver$1;)V
	// Method access$200 (Lorg/apache/wicket/util/xml/CustomEntityResolver$EntityKey;)Ljava/lang/String;
	log.debug("   Method " + name + " " + desc);
	return null;
    }

    public void visitEnd() {
	log.debug("Visited " + jclass.getFullyQualifiedName());
    }
}
