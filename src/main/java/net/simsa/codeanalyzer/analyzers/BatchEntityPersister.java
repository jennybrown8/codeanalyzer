package net.simsa.codeanalyzer.analyzers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.persistence.EntityManager;

public class BatchEntityPersister {

    static final int BATCH_SIZE = 100;

    @Inject
    EntityManager em;

    List<Object> list;

    public BatchEntityPersister() {
	list = new ArrayList<Object>();
    }

    @PreDestroy
    public void onPreDestroy() {
	em.close();
    }

    public void add(Object entity) {
	list.add(entity);
	if (list.size() > BATCH_SIZE) {
	    sendList();
	}
    }

    public void completeFinalSave() {
	sendList();
    }

    protected void sendList() {
	em.getTransaction().begin();
	for (Object e : list) {
	    em.persist(e);
	}
	em.getTransaction().commit();
	list.clear();
    }

}
