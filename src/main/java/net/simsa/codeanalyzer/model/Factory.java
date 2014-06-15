package net.simsa.codeanalyzer.model;

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
	return Persistence.createEntityManagerFactory("net.simsa.codeanalyzer.PU");
    }
    
}
