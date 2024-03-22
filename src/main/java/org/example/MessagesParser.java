package org.example;

import org.example.dao.BreakdownInfosDAO;
import org.example.dao.ComputerDAO;
import org.example.dao.EventsMessagesAssociationDAO;
import org.example.models.BreakdownInfos;
import org.example.models.Computer;
import org.example.models.EventsMessagesAssociation;
import org.example.tools.BreakdownXML;
import org.example.tools.ConfigurationManager;
import org.example.tools.DiagnosticFileDecoder;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Cette classe complète le fonctionnement de la classe MessageListener,
 * son role est d'extraire les informations des messages déjà captures et enregistres par Message Listener
 */
public class MessagesParser implements Runnable {
    private static boolean DEBUG = true;
    private BufferedReader tmpReader;
    private Pattern pattern = Pattern.compile("(plant:(\\d) *uic:(\\d) *code:([\\dA-Fa-f]{0,4}))|(codice:([\\dA-Fa-f]{0,4}))");
    // -------------------------------------------------------
    private EventsMessagesAssociationDAO eventsMessagesAssociationDAO = new EventsMessagesAssociationDAO();
    private BreakdownInfosDAO breakdownInfosDAO = new BreakdownInfosDAO();
    private ComputerDAO computerDAO = new ComputerDAO();
    // -------------------------------------------------------
    private Computer computer;
    public MessagesParser(File tmp) throws FileNotFoundException {
        tmpReader = new BufferedReader(new FileReader(tmp));
        // at the construction of the object we need to keep track of the current computer infos
        // we get them from the database
        computer = computerDAO.getById(
                Long.valueOf(
                        (String) ConfigurationManager.properties.get("computer-id")));
    }
    @Override
    public void run() {
        int offset = 0;
        while (true) {
            // processing
            try {
                String line = tmpReader.readLine();
                if (line != null) {
                    Matcher matcher = pattern.matcher(line);
                    // we got a hit
                    if (matcher.find()) {
                        // we extract the event code
                        String code = matcher.group(4).toUpperCase();
                        BreakdownInfos breakdownInfos = new BreakdownInfos();
                        breakdownInfos.setCode(code);
                        // we fetch the event object from the database
                        breakdownInfos = breakdownInfosDAO.search(breakdownInfos).getFirst();

                        if (DEBUG) {
                            System.out.println("+ Parser / code: " + code);
                            System.out.println("+ Parser / event from database: " + breakdownInfos);
                            System.out.println("+ Parser / date/time: " + LocalDateTime.now());
                        }
                        // --- insert into the database ---
                        // we create the object that represent the event
                        EventsMessagesAssociation message = new EventsMessagesAssociation();
                        message.setDate(LocalDateTime.now());
                        message.setRame(computer.getRameComputer().getFirst().getRame());
                        message.setBreakdownInfos(breakdownInfos);
                        // save to the database
                        eventsMessagesAssociationDAO.create(message);
                        //---------------------------------
                    }
                    // si on a rien capture
                    else
                    {
                        if (DEBUG)
                            System.out.println("+ Parser: Not found");
                    }
                }
                Thread.sleep(2000);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
