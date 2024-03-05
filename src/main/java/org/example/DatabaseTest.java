package org.example;

import org.example.Models.Computer;
import org.example.Models.Rame;
import org.example.Models.RameComputerAssociation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.sql.SQLException;

public class DatabaseTest {
    public void run() throws SQLException, IOException {
        org.h2.tools.Server.createWebServer().start();
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        Session session =  sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Rame rame = new Rame();
        rame.setId(2);
        rame.setName("train-0");

        Computer computer = new Computer();
        computer.setId(2);

        RameComputerAssociation association =new RameComputerAssociation();
        association.setId(2);
        association.setRame(rame);
        association.setComputer(computer);

        session.persist(rame);
        session.persist(computer);
        session.persist(association);

        transaction.commit();
        session.close();

        session = sessionFactory.openSession();
        session.beginTransaction();
        Rame first  = (Rame) (session.createQuery("FROM Rame r" ).list().getFirst());
        System.out.println(first.getComputerAssociation().getId());
        session.close();
        while (true)
            System.in.read();
    }
}
