package com.biblio.dao.impl;

import com.biblio.dao.IResponseSupportDAO;
import com.biblio.entity.ResponseSupport;
import com.biblio.jpaconfig.JpaConfig;

import javax.persistence.EntityManager;

public class ResponseSupportDAOImpl implements IResponseSupportDAO {
    @Override
    public ResponseSupport findBySupportId(Long supportId) {
        String jpql = "SELECT r FROM ResponseSupport r WHERE r.support.id = :supportId";
        return JpaConfig.getEntityManager().createQuery(jpql, ResponseSupport.class)
                .setParameter("supportId", supportId).getResultStream().findFirst().orElse(null);
    }

    @Override
    public void save(ResponseSupport responseSupport) {
        EntityManager em = JpaConfig.getEntityManager();
        em.getTransaction().begin();
        em.persist(responseSupport);
        em.getTransaction().commit();
        em.close();
    }
}
