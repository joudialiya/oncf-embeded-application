package org.example.tools;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.Writer;

/* this class recreate the function of taf view, it provides a static method that
takes the encoded file and decode it into csv file */
public class DiagnosticFileDecoder {
    private static final boolean DEBUG = true;
    public String ResourcePath = null;
    public void decode(String filename, String outputFilename)
    {
        try {
            InputStream in = new FileInputStream(filename);
            PrintStream out = new PrintStream(outputFilename);
            boolean endOfFile = false;
            int recordNumber = 0;

            while(true)
            {
                if (DEBUG)
                    System.out.println("+ Record number: " + recordNumber);
                byte[] record = new byte[16];
                int localFFCount = 0;
                for (int  i = 0; i < 16; ++i) {
                    int b = in.read();
                    if (DEBUG)
                        System.out.print(String.format("%02x", b & 0xff));
                    if ((b & 0xff) == 0xff)
                    {
                        localFFCount++;
                    }
                    if (b == -1) {
                        if (DEBUG)
                            System.out.println("\n+ End of file");
                        endOfFile = true;
                        break;
                    }
                    else
                        record[i] = (byte) (b & 0xff);
                }
                recordNumber++;
                if (DEBUG)
                    System.out.println();
                if (localFFCount >= 15)
                    break;
                if (endOfFile)
                    break;

                // add the base infos extracted from the diagnostic file
                DiagnosticRecord diagnosticRecord = new DiagnosticRecord(record);
                writeMainDiagnosticInfos(out, diagnosticRecord);
                // add information about the breakdown code that we can get form the Resource.xml file
                BreakdownXML breakdownXML = getCodeDescription(diagnosticRecord.getCode());
                writeSecondaryInfos(out, breakdownXML);

            }
        }
        catch (Exception e)
        {
            e.getStackTrace();
        }
    }
    private static void writeMainDiagnosticInfos(PrintStream out, DiagnosticRecord diagnosticRecord)
    {
        if (DEBUG)
            System.out.println("+ Write vehicle number");
        out.print(diagnosticRecord.getType());
        out.print(";");
        if (DEBUG)
            System.out.println("+ Write vehicle number");
        out.print(diagnosticRecord.getVehicleNumber());
        out.print(";");
        if (DEBUG)
            System.out.println("+ Write date");
        out.print(diagnosticRecord.getDate());
        out.print(";");
        if (DEBUG)
            System.out.println("+ Write time");
        out.print(diagnosticRecord.getTime());
        out.print(";");
        if (DEBUG)
            System.out.println("+ Write velocity");
        out.print(diagnosticRecord.getVelocity());
        out.print(";");
        if (DEBUG)
            System.out.println("+ Write line tension");
        out.print(diagnosticRecord.getLineTension());
        out.print(";");
        if (DEBUG)
            System.out.println("+ Write code");
        out.print(diagnosticRecord.getCode());
        out.print(";");
    }
    private static void writeSecondaryInfos(PrintStream out, BreakdownXML breakdownXML)
    {
        if (DEBUG)
            System.out.println("Write Device");
        if (breakdownXML != null)
            out.print(breakdownXML.getDevice());
        out.print(";");
        if (DEBUG)
            System.out.println("Write description");
        if (breakdownXML != null)
            out.print(breakdownXML.getDescription());
        out.print(";");
        if (DEBUG)
            System.out.println("Write PDM");
        if (breakdownXML != null)
            out.print(breakdownXML.getPdm());
        out.print(";");
        if (DEBUG)
            System.out.println("Write PDO");
        if (breakdownXML != null)
            out.print(breakdownXML.getPdo());
        out.print('\n');
    }
    public  BreakdownXML getCodeDescription(String code)
    {
        if (DEBUG)
            System.out.println("Getting description for the code: " + code);
        try
        {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            // load the document
            Document doc = documentBuilder.parse("Resource.xml");
            // obtenir les elements a partir du fichier XML qui represent les pannes
            NodeList items = doc.getElementsByTagName("item");
            // iterate through all the breakdown elements searching for a match
            for(int i = 0; i < items.getLength(); ++i) {
                Node item = items.item(i);
                // get the item code
                String itemCode = item.getAttributes().item(0).getTextContent().substring(2);
                // checking for the match
                if (itemCode.equals(code)) {
                    if (DEBUG)
                        System.out.println("Code found");
                    BreakdownXML breakdownXML = new BreakdownXML();
                    //---------------Set the breakdownXML object that represent the breakdown infos----------------
                    breakdownXML.setCode(code);
                    breakdownXML.setDevice(item.getParentNode().getNodeName());
                    breakdownXML.setDescription(item.getChildNodes().item(1).getTextContent().strip());
                    breakdownXML.setPdm(item.getChildNodes().item(2).getTextContent().strip());
                    breakdownXML.setPdo(item.getChildNodes().item(3).getTextContent().strip());
                    //---------------------------------------------------------------------------------------------
                    return breakdownXML;
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        if (DEBUG)
            System.out.println("Code not found");
        return null;
    }
}
