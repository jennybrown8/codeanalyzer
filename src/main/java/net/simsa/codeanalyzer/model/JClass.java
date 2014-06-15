package net.simsa.codeanalyzer.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * Models a class and what we know about it from binary/source analysis
 * 
 */
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
    Integer superclass_id;

    public JClass() {

    }

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

    public Integer getSuperclass_id() {
	return superclass_id;
    }

    public void setSuperclass_id(Integer superclass_id) {
	this.superclass_id = superclass_id;
    }

}
