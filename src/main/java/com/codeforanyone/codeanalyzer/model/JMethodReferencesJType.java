package com.codeforanyone.codeanalyzer.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class JMethodReferencesJType {
    Integer id;
    JMethod jMethod;
    String jTypeName;
    
    public JMethodReferencesJType() {
	
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

    @ManyToOne
    @JoinColumn(name="jmethod_id")
    public JMethod getJMethod() {
        return jMethod;
    }

    public void setJMethod(JMethod jMethod) {
        this.jMethod = jMethod;
    }

    public String getjTypeName() {
        return jTypeName;
    }

    public void setjTypeName(String jTypeName) {
        this.jTypeName = jTypeName;
    }


    
    
}
