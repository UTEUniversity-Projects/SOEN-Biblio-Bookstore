package com.biblio.jpaconfig;

import com.biblio.connection.DBConnection;
import com.biblio.constants.Constant;

import org.hibernate.jpa.HibernatePersistenceProvider;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

import static org.hibernate.cfg.AvailableSettings.*;
import static org.hibernate.cfg.AvailableSettings.AUTOCOMMIT;

public class JpaConfig {

    private static final EntityManagerFactory enma = new HibernatePersistenceProvider()
            .createContainerEntityManagerFactory(new DBConnection(), config());

    private static Map<String, Object> config() {
        Map<String, Object> map = new HashMap<>();

        map.put(JPA_JDBC_DRIVER, Constant.DB_DRIVER);
        map.put(JPA_JDBC_URL, Constant.DB_URL);
        map.put(JPA_JDBC_USER, Constant.USERNAME);
        map.put(JPA_JDBC_PASSWORD, Constant.PASSWORD);
        map.put(DIALECT, org.hibernate.dialect.MySQL8Dialect.class);
        map.put(HBM2DDL_AUTO, "update");
       // map.put(SHOW_SQL, "true");
        map.put(FORMAT_SQL, "true");
        map.put(QUERY_STARTUP_CHECKING, "true");
        map.put(GENERATE_STATISTICS, "false");
        map.put(USE_REFLECTION_OPTIMIZER, "false");
        map.put(USE_SECOND_LEVEL_CACHE, "false");
        map.put(USE_QUERY_CACHE, "false");
        map.put(USE_STRUCTURED_CACHE, "false");
        map.put(STATEMENT_BATCH_SIZE, "20");
        map.put(AUTOCOMMIT, "false");

        map.put("hibernate.hikari.minimumIdle", Constant.DB_MIN_CONNECTIONS);
        map.put("hibernate.hikari.maximumPoolSize", Constant.DB_MAX_CONNECTIONS);
        map.put("hibernate.hikari.idleTimeout", "30000");

        return map;
    }

    public static EntityManager getEntityManager() {
        return enma.createEntityManager();
    }

    public static void closeEntityManagerFactory() {
        if (enma != null) {
            enma.close();
        }
    }

    public static void main(String[] args) {
        System.out.println(JpaConfig.getEntityManager());
    }
}
