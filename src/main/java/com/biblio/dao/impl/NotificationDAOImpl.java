package com.biblio.dao.impl;

import com.biblio.dao.INotificationDAO;
import com.biblio.entity.Notification;
import com.biblio.jpaconfig.JpaConfig;

import javax.persistence.EntityManager;

public class NotificationDAOImpl extends GenericDAOImpl<Notification> implements INotificationDAO {

    public NotificationDAOImpl() {
        super(Notification.class);
    }

    @Override
    public void saveSupport_Notification(Notification notification) {
        EntityManager entityManager = JpaConfig.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(notification);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Notification update(Notification notification) {
        return super.update(notification);
    }

    @Override
    public Notification findNotificationById(Long id) {
        return super.findById(id);
    }

    @Override
    public Notification insert(Notification notification) {
       return super.save(notification);
    }

    public static void main(String[] args) {
        NotificationDAOImpl dao = new NotificationDAOImpl();
        Notification notification = dao.findNotificationById(1L);
        System.out.println(notification);
    }
}
