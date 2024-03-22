package org.example.tools;

import org.example.dao.BreakdownInfosDAO;
import org.example.models.BreakdownInfos;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/* This class will init the database*/
public class DatabaseInit {

    public static void loadBreakdownInfos() throws IOException, SAXException, ParserConfigurationException {
        // dao
        BreakdownInfosDAO breakdownInfosDAO = new BreakdownInfosDAO();
        // load the document
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document resourceDoc = documentBuilder.parse("Resource.xml");
        // obtenir les elements a partir du fichier XML qui represent les pannes
        NodeList items = resourceDoc.getElementsByTagName("item");
        for (int i = 0; i < items.getLength(); ++i)
        {
            Node item = items.item(i);
            String itemCode = item.getAttributes().item(0).getTextContent().substring(2);

            BreakdownInfos breakdownInfos = new BreakdownInfos();
            breakdownInfos.setCode(itemCode);
            breakdownInfos.setDevice(item.getParentNode().getNodeName());
            breakdownInfos.setDescription(item.getChildNodes().item(1).getTextContent().strip());
            //------- insert it to the database --------
            breakdownInfosDAO.create(breakdownInfos);
        }
    }
}
