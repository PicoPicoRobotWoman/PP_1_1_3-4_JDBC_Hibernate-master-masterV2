package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class Util {
    // реализуйте настройку соеденения с БД
    private static final String BD_DRIVERS = "com.mysql.cj.jdbc.Driver";
    private static final String BD_URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String BD_USER = "root";
    private static final String BD_PASSWORD = "TimBurton666";
    private static Connection JBDCconnection;
    private static SessionFactory hibernateSessionFactory;


    public static Connection getJBDCConnection() {

        try {
            if( JBDCconnection == null || JBDCconnection.isClosed()) {
                Class.forName(BD_DRIVERS);
                JBDCconnection = DriverManager.getConnection(BD_URL,BD_USER,BD_PASSWORD);
                JBDCconnection.setAutoCommit(false);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return JBDCconnection;

    }

    public static SessionFactory getHibernateSessionFactory() {

        if(hibernateSessionFactory == null || !hibernateSessionFactory.isOpen() ) {
            try {
                Configuration config = new Configuration();

                Properties settings = new Properties();
                settings.put(Environment.DRIVER, BD_DRIVERS);
                settings.put(Environment.URL, BD_URL);
                settings.put(Environment.USER, BD_USER);
                settings.put(Environment.PASS, BD_PASSWORD);

                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.FORMAT_SQL, "true");
                settings.put(Environment.HBM2DDL_AUTO, "create-drop");

                config.setProperties(settings);
                config.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();

                hibernateSessionFactory = config.buildSessionFactory(serviceRegistry);
            } catch (Exception se ) {
                se.printStackTrace();
            }
        }
        return hibernateSessionFactory;
    }

    public static void close() {
        try {
            if (JBDCconnection != null && !JBDCconnection.isClosed()) {
                JBDCconnection.close();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }

        try {
            if (hibernateSessionFactory != null && !hibernateSessionFactory.isClosed()) {
                hibernateSessionFactory.close();
            }
        } catch (HibernateException se) {
            se.printStackTrace();
        }

    }

}
