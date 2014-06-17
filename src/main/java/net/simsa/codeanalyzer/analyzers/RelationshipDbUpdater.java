package net.simsa.codeanalyzer.analyzers;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class RelationshipDbUpdater {

    @Inject
    EntityManager em;

    public RelationshipDbUpdater() {

    }

    /**
     * Re-clean the database for the next run
     */
    public void before() {
	em.getTransaction().begin();
	em.createNativeQuery("delete from JClass").executeUpdate();
	em.getTransaction().commit();

    }

    /*
     *  After entities are created, this builds relationships between then using bulk updates. 
     */
    public void after() {
	em.getTransaction().begin();
	em.createNativeQuery(
		"update JClass join JClass JClassChild on JClassChild.superclassName = JClass.fullyQualifiedName "
			+ "set JClassChild.superclassId = JClass.id").executeUpdate();
	em.getTransaction().commit();
    }

}
