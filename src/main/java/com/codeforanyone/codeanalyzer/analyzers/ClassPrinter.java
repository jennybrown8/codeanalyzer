package com.codeforanyone.codeanalyzer.analyzers;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/** Example from ObjectWeb ASM tutorial */
public class ClassPrinter extends ClassVisitor {
    
    MethodVisitor mvisit;
    
    public ClassPrinter() {
	super(Opcodes.ASM4);
	mvisit = new MethodPrinter(Opcodes.ASM4);
    }

    public void visit(int version, int access, String name, String signature, String superName,
	    String[] interfaces) {
	System.out.println("Class definition: " + name + " extends " + superName + " {");
    }

    public void visitSource(String source, String debug) {
	System.out.println("   Source " + source);
    }

    public void visitOuterClass(String owner, String name, String desc) {
	System.out.println("   Outer class " + owner + " - " + name);
    }

    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
	System.out.println("   Annotation " + desc);
	return null;
    }

    public void visitAttribute(Attribute attr) {
	System.out.println("   Attribute " + attr.toString());
    }

    public void visitInnerClass(String name, String outerName, String innerName, int access) {
	System.out.println("   Inner class " + name + " , " + outerName + " , " + innerName);
    }

    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
	System.out.println("   Field " + desc + " " + name + " " + signature + " " + value);
	return null;
    }

    public MethodVisitor visitMethod(int access, String name, String desc, String signature,
	    String[] exceptions) {
	System.out.println("   Method " + name + desc);
	return mvisit;
    }

    public void visitEnd() {
	System.out.println("}");
    }

    
    public static class MethodPrinter extends MethodVisitor {

	public MethodPrinter(int api, MethodVisitor mv) {
	    super(api, mv);
	}

	public MethodPrinter(int api) {
	    super(api);
	}

	@Override
	public void visitParameter(String name, int access) {
	    super.visitParameter(name, access);
	    System.out.println("Method parameter " + name);
	}

	@Override
	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
	    System.out.println("Annotation " + desc);
	    return super.visitAnnotation(desc, visible);
	}

	@Override
	public void visitTypeInsn(int opcode, String type) {
	    super.visitTypeInsn(opcode, type);
	    System.out.println("Type Insn " + type);
	}

	@Override
	public void visitFieldInsn(int opcode, String owner, String name, String desc) {
	    super.visitFieldInsn(opcode, owner, name, desc);
	    System.out.println("Field Insn " + owner + "," + name + "," + desc);
	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
	    super.visitMethodInsn(opcode, owner, name, desc, itf);
	    System.out.println("Method Insn " + owner + "," + name + "," + desc + "," + itf);
	}

	@Override
	public void visitInvokeDynamicInsn(String name, String desc, Handle bsm, Object... bsmArgs) {
	    super.visitInvokeDynamicInsn(name, desc, bsm, bsmArgs);
	    System.out.println("InvokeDynamic Insn " + name + "," + desc + "," + bsm.toString());
	}

	@Override
	public void visitLocalVariable(String name, String desc, String signature, Label start, Label end,
		int index) {
	    super.visitLocalVariable(name, desc, signature, start, end, index);
	    System.out.println("Local Variable " + name + "," + desc + "," + signature);
	}

	
	
    }

}
