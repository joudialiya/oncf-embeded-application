package org.example;



import org.example.tools.ConfigurationManager;
import org.example.tools.DatabaseInit;
import org.example.tools.HibernateUtils;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, IOException, InterruptedException, ParserConfigurationException, SAXException {
        // init the embedded database server
        org.h2.tools.Server.createWebServer().start();
        // init hibernate
        HibernateUtils.init();
        // init configuration manager
        ConfigurationManager.init("application.properties");
        // we load the events data from the resource file
        DatabaseInit.loadBreakdownInfos();
        // on commence le test
        new ListenerTest().run();

        while (true) {
            Thread.sleep(2000);
        }
    }
}