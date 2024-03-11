import com.fazecast.jSerialComm.SerialPort;
import org.example.tools.BreakdownXML;
import org.example.tools.DiagnosticFileDecoder;
import org.example.tools.XmodemReceiver;
import org.hibernate.dialect.SybaseASEDialect;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class TestClass {
    @Test
    public void inputStreamPort()
    {
        var com3 = SerialPort.getCommPort("COM3");
        var com4 = SerialPort.getCommPort("COM4");
        com3.openPort();
        com4.openPort();
        com4.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 5000, 2);
        try {
            Thread.sleep(150);
            System.out.println(com4.readBytes(new byte[3], 3));

        }catch (Exception e) {
            e.getStackTrace();
        }

    }
    @Test
    public void testXmodemReceiverCNCVersion() throws IOException, InterruptedException {
        var com4 = SerialPort.getCommPort("COM4");
        com4.openPort();
        XmodemReceiver.receive(com4, "out.txt");
    }
    @Test
    public void testDecoding()
    {
        DiagnosticFileDecoder diagnosticFileDecoder = new DiagnosticFileDecoder();
        diagnosticFileDecoder.ResourcePath = "Resource.xml";
        diagnosticFileDecoder.decode("output", "out.csv");
    }
    @Test
    public void loadXMLDocument() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = documentBuilder.parse("Resource.xml");
        NodeList items = doc.getElementsByTagName("item");
        for(int i = 0; i < items.getLength(); ++i)
        {
            Node item = items.item(i);
            System.out.println(item.getAttributes().item(0).getTextContent().substring(2));
            System.out.print(item.getChildNodes().item(1).getTextContent());
            System.out.print(" - ");
            System.out.println(item.getParentNode().getNodeName());
        }
    }
    @Test
    public void testGetCodeDescription()
    {
        DiagnosticFileDecoder diagnosticFileDecoder = new DiagnosticFileDecoder();
        diagnosticFileDecoder.ResourcePath = "Resource.xml";
        BreakdownXML breakdown = diagnosticFileDecoder.getCodeDescription("400B");
        System.out.println(breakdown.getDescription() + ";" + breakdown.getPdo().length() + ";" +breakdown.getPdm());
    }

}

