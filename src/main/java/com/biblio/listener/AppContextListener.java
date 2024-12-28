package com.biblio.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.biblio.jpaconfig.JpaConfig;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        JpaConfig.getEntityManager();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        JpaConfig.closeEntityManagerFactory();

        com.mysql.cj.jdbc.AbandonedConnectionCleanupThread.checkedShutdown();
    }
}
