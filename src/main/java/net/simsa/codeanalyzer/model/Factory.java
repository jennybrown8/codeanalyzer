package net.simsa.codeanalyzer.model;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Factory {
    
    @Inject
    EntityManagerFactory entityManagerFactory;
    
    @Produces
    public EntityManager createEntityManager() {
	return entityManagerFactory.createEntityManager();
    }
    
    @Produces
    public static EntityManagerFactory createEntityManagerFactory() {
	Map<String, String> properties = new HashMap<String, String>();
	ResourceBundle propsFile = ResourceBundle.getBundle("db");
	for (String key : propsFile.keySet()) {
	    properties.put(key, propsFile.getString(key));
	}
	return Persistence.createEntityManagerFactory("net.simsa.codeanalyzer.PU", properties);
    }
    
}
