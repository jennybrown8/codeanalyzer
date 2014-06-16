package net.simsa.codeanalyzer.analyzers;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import net.simsa.codeanalyzer.model.QJClass;

public class RelationshipDbUpdater {

    @Inject
    EntityManager em;

    public RelationshipDbUpdater() {

    }

    public void run() {
	em.getTransaction().begin();
	em.createNativeQuery(
		"update JClass join JClass JClassChild on JClassChild.superclassName = JClass.fullyQualifiedName " +
		"set JClassChild.superclassId = JClass.id")
		.executeUpdate();
	em.getTransaction().commit();
    }

}
