package net.simsa.codeanalyzer.analyzers;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/** Example from ObjectWeb ASM tutorial */
public class ClassPrinter extends ClassVisitor {
    public ClassPrinter() {
	super(Opcodes.ASM4);
    }

    public void visit(int version, int access, String name, String signature, String superName,
	    String[] interfaces) {
	System.out.println(name + " extends " + superName + " {");
    }

    public void visitSource(String source, String debug) {
	System.out.println("Source " + source);
    }

    public void visitOuterClass(String owner, String name, String desc) {
	System.out.println("Outer class " + owner + " - " + name);
    }

    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
	System.out.println("Annotation " + desc);
	return null;
    }

    public void visitAttribute(Attribute attr) {
    }

    public void visitInnerClass(String name, String outerName, String innerName, int access) {
	System.out.println("Inner class " + name + " , " + outerName + " , " + innerName);
    }

    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
	System.out.println(" " + desc + " " + name);
	return null;
    }

    public MethodVisitor visitMethod(int access, String name, String desc, String signature,
	    String[] exceptions) {
	System.out.println(" " + name + desc);
	return null;
    }

    public void visitEnd() {
	System.out.println("}");
    }
}
