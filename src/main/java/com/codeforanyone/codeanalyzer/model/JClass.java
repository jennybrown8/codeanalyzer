package com.codeforanyone.codeanalyzer.model;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

/**
 * Models a class and what we know about it from binary/source analysis
 * 
 */
@Entity
public class JClass {
    
    Integer id;
    String fullyQualifiedName;
    String simpleName;
    String sourcePath;
    String sourceFile;
    String packageName;
    String packageId;
    String organization;
    String superclassName;
    Integer superclassId;
    
    String[] interfaces;

    Set<JClassImplementsJInterface> implementedInterfaces;
    Set<JMethod> jMethods;
    
    public JClass() {
	implementedInterfaces = new LinkedHashSet<JClassImplementsJInterface>();
	jMethods = new LinkedHashSet<JMethod>();
    }

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public String getFullyQualifiedName() {
	return fullyQualifiedName;
    }

    public void setFullyQualifiedName(String fullyQualifiedName) {
	this.fullyQualifiedName = fullyQualifiedName;
    }
    
    public void setInterfaces(String[] interfaces) {
	this.interfaces = interfaces;
	for (String s : interfaces) {
	    JClassImplementsJInterface relation = new JClassImplementsJInterface();
	    relation.setJClass(this);
	    relation.setInterfaceName(s);
	    implementedInterfaces.add(relation);
	}
    }
    
    public void addMethod(JMethod jmethod) {
	jMethods.add(jmethod);
    }

    public String getSimpleName() {
	return simpleName;
    }

    public void setSimpleName(String simpleName) {
	this.simpleName = simpleName;
    }

    public String getSourcePath() {
	return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
	this.sourcePath = sourcePath;
    }

    public String getSourceFile() {
	return sourceFile;
    }

    public void setSourceFile(String sourceFile) {
	this.sourceFile = sourceFile;
    }

    public String getPackageName() {
	return packageName;
    }

    public void setPackageName(String packageName) {
	this.packageName = packageName;
    }

    public String getPackageId() {
	return packageId;
    }

    public void setPackageId(String packageId) {
	this.packageId = packageId;
    }

    public String getOrganization() {
	return organization;
    }

    public void setOrganization(String organization) {
	this.organization = organization;
    }

    public String getSuperclassName() {
	return superclassName;
    }

    public void setSuperclassName(String superclassName) {
	this.superclassName = superclassName;
    }

    public Integer getSuperclassId() {
	return superclassId;
    }

    public void setSuperclassId(Integer superclassId) {
	this.superclassId = superclassId;
    }

    @OneToMany(mappedBy="JClass", cascade=CascadeType.ALL)
    public Set<JClassImplementsJInterface> getImplementedInterfaces() {
        return implementedInterfaces;
    }

    public void setImplementedInterfaces(Set<JClassImplementsJInterface> implementedInterfaces) {
        this.implementedInterfaces = implementedInterfaces;
    }
    
    @OneToMany(mappedBy="jClass", cascade=CascadeType.ALL) 
    public Set<JMethod> getJMethods() {
	return jMethods;
    }
    
    public void setJMethods(Set<JMethod> jmethods) {
	this.jMethods = jmethods;
    }

}
