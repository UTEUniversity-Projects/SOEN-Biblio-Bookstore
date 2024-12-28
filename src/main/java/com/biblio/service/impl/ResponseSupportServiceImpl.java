package com.biblio.service.impl;

import com.biblio.dao.*;
import com.biblio.dao.impl.*;
import com.biblio.entity.*;
import com.biblio.enumeration.ENotificationStatus;
import com.biblio.enumeration.ENotificationType;
import com.biblio.jpaconfig.JpaConfig;
import com.biblio.service.IResponseSupportService;

import javax.persistence.EntityManager;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

public class ResponseSupportServiceImpl implements IResponseSupportService {

    private final ISupportDAO supportDAO = new SupportDAOImpl();
    private final IResponseSupportDAO responseSupportDAO = new ResponseSupportDAOImpl();
    private final IStaffDAO staffDAO = new StaffDAOImpl();
    private  final INotificationDAO notificationDAO = new NotificationDAOImpl();
    private  final ICustomerDAO customerDAO = new CustomerDAOImpl();
    public List<Support> getAllSupports() {
        return supportDAO.findAll();
    }

    public Support getSupportById(Long id) {
        return supportDAO.findById(id);
    }

    public ResponseSupport getResponseSupportBySupportId(Long supportId) {
        return responseSupportDAO.findBySupportId(supportId);
    }

    public void saveResponseSupport(String title, String content, Long staffId, Long supportId) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty.");
        }

        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Content cannot be null or empty.");
        }

        EntityManager em = JpaConfig.getEntityManager();
        try {
            em.getTransaction().begin();

            // Fetch entities
            Staff staff = em.find(Staff.class, staffId);
            Support support = em.find(Support.class, supportId);
            if (staff == null || support == null) {
                throw new RuntimeException("Staff or Support not found for given IDs.");
            }

            Customer customer = support.getCustomer();
            if (customer == null) {
                throw new RuntimeException("Associated customer not found for the support request.");
            }

            // Create and persist ResponseSupport
            ResponseSupport responseSupport = new ResponseSupport();
            responseSupport.setTitle(title);
            responseSupport.setContent(content);
            responseSupport.setCreatedAt(LocalDateTime.now());
            responseSupport.setStaff(staff);
            responseSupport.setSupport(support);
            em.persist(responseSupport);

            // Create and persist Notification
            Notification notification = createSupportNotification(responseSupport.getContent(), customer);
            em.persist(notification);

            // Update customer notifications
            customer.getNotifications().add(notification);

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error saving ResponseSupport: " + e.getMessage(), e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    private Notification createSupportNotification(String responseContent, Customer customer) {
        Notification notification = new Notification();
        notification.setCreatedAt(LocalDateTime.now());
        notification.setSentTime(LocalDateTime.now());
        notification.setTitle("Your support request has been responded to");
        notification.setContent("We have responded to your request: " + responseContent);
        notification.setType(ENotificationType.SUPPORT);
        notification.setStatus(ENotificationStatus.NOT_SEEN);
        notification.setCustomer(customer);
        return notification;
    }


    public Staff getStaffById(Long id) {
        return staffDAO.findById(id); // Add a findById method in your StaffDAO
    }


}
