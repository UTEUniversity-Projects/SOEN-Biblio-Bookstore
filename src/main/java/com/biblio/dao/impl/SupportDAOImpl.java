package com.biblio.dao.impl;

import com.biblio.dao.ISupportDAO;
import com.biblio.entity.Support;
import com.biblio.jpaconfig.JpaConfig;


import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class SupportDAOImpl implements ISupportDAO {

    @Override
    public List<Support> findAll() {
        EntityManager entityManager = JpaConfig.getEntityManager();  // Get EntityManager from JpaConfig
        List<Support> supportList = null;

        try {
            // Use the EntityManager to create and execute the query
            TypedQuery<Support> query = entityManager.createQuery("from Support", Support.class);
            supportList = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace(); // Log any exceptions
        } finally {
            entityManager.close(); // Close the EntityManager after the query
        }

        return supportList;
    }


    @Override
    public Support findById(Long id) {
        EntityManager entityManager = JpaConfig.getEntityManager();
        Support support = null;

        try {
            support = entityManager.find(Support.class, id);
        } finally {
            entityManager.close();
        }

        return support;
    }
    @Override
    public Support save(Support support) {
        EntityManager entityManager = JpaConfig.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            support = entityManager.merge(support); // Sử dụng merge thay vì persist
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return support;
    }

//    @Override
//    public void update(Support support) {
//        EntityManager entityManager = JpaConfig.getEntityManager();
//
//        try {
//            entityManager.getTransaction().begin();
//            entityManager.merge(support);
//            entityManager.getTransaction().commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//            if (entityManager.getTransaction().isActive()) {
//                entityManager.getTransaction().rollback();
//            }
//        } finally {
//            entityManager.close();
//        }
//    }

}
