package com.codeforanyone.codeanalyzer.model;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

/**
 * Models a method and what we know about it from binary/source analysis
 * 
 */
@Entity
public class JMethod {
    
    Integer id;
    String methodName;
    JClass jClass;
    Set<JMethodReferencesJType> referencedTypes;
    
    public JMethod() {
	referencedTypes = new LinkedHashSet<JMethodReferencesJType>();
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


    public void setReferencedTypes(String[] referencedTypeNames) {
	for (String ref : referencedTypeNames) {
	    JMethodReferencesJType relation = new JMethodReferencesJType();
	    relation.setJMethod(this);
	    relation.setjTypeName(ref);
	    referencedTypes.add(relation);
	}
    }
    
    public void addReferencedType(String referencedTypeName) {
	    JMethodReferencesJType relation = new JMethodReferencesJType();
	    relation.setJMethod(this);
	    relation.setjTypeName(referencedTypeName);
	    referencedTypes.add(relation);
    }

    @ManyToOne
    @JoinColumn(name="jclass_id")
    public JClass getjClass() {
        return jClass;
    }

    public void setjClass(JClass jClass) {
        this.jClass = jClass;
    }
    

    @OneToMany(mappedBy="JMethod", cascade=CascadeType.ALL)
    public Set<JMethodReferencesJType> getReferencedTypes() {
        return referencedTypes;
    }

    public void setReferencedTypes(Set<JMethodReferencesJType> referencedTypes) {
        this.referencedTypes = referencedTypes;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }


    
}
