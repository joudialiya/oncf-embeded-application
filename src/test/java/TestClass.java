import com.fazecast.jSerialComm.SerialPort;
import org.example.MessagesListener;
import org.example.MessagesParser;
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
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestClass {
    @Test
    // test de la reception d'un fichier via Xmodem CRC la version 1
    public void testXmodemReceiverCNCVersion() throws IOException, InterruptedException {
        var com4 = SerialPort.getCommPort("COM4");
        com4.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 10000, 0);
        com4.openPort();
        XmodemReceiver.receive(com4, "out.txt");
    }
    @Test
    // test de la reception d'un fichier via Xmodem CRC la version 2
    public void testXmodemReceiverCNCVersionV2() throws IOException, InterruptedException {
        var com4 = SerialPort.getCommPort("COM4");
        com4.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 10000, 0);
        com4.openPort();
        XmodemReceiver.receiveV2(com4, "out.txt");
    }
    @Test
    // test du transcodage du releve du CCU
    public void testDecoding()
    {
        DiagnosticFileDecoder diagnosticFileDecoder = new DiagnosticFileDecoder();
        diagnosticFileDecoder.resourcePath = "Resource.xml";
        diagnosticFileDecoder.decode("output", "out.csv");
    }
    @Test
    // test de l'extration des infos lie a un code d'evenement
    public void testGetCodeDescription() throws Exception {
        DiagnosticFileDecoder diagnosticFileDecoder = new DiagnosticFileDecoder();
        diagnosticFileDecoder.resourcePath = "Resource.xml";
        BreakdownXML breakdown = diagnosticFileDecoder.getCodeDescription("400B");
        System.out.println(breakdown.getDescription() + ";" + breakdown.getPdo().length() + ";" +breakdown.getPdm());
    }
    @Test
    // test de l'expretion regulier qui va capturer les infos
    public void testRegEx() throws IOException {
        InputStream inputStream = new FileInputStream("breakdown.txt");

        Pattern pattern = Pattern.compile("(plant:(\\d) *uic:(\\d) *code:([\\dA-Fa-f]{0,4}))|(codice:([\\dA-Fa-f]{0,4}))");
        Matcher matcher = pattern.matcher(new String(inputStream.readAllBytes()));
        while (matcher.find())
        {
            System.out.println(matcher.group(0));
        }
    }

}

