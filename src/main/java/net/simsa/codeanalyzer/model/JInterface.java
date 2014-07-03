package net.simsa.codeanalyzer.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class JInterface {
    
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

}
