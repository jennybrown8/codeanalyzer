package com.codeforanyone.codeanalyzer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class JClassImplementsJInterface {
    Integer id;
    JClass jClass;
    String interfaceName;
    JInterface jInterface;
    
    public JClassImplementsJInterface() {
	
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
    @JoinColumn(name="jclass_id")
    public JClass getJClass() {
        return jClass;
    }

    public void setJClass(JClass jClass) {
        this.jClass = jClass;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    @OneToOne
    public JInterface getJInterface() {
        return jInterface;
    }

    public void setJInterface(JInterface jinterface) {
        this.jInterface = jinterface;
    }
    
}
