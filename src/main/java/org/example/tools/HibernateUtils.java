package org.example.tools;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/* this class provide the session object with is essential for database transactions*/
public class HibernateUtils {
    public static Session session;
    public static void init()
    {
        HibernateUtils.session = new Configuration().configure().buildSessionFactory().openSession();
    }
}
