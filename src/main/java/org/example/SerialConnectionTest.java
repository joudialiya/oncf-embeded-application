package org.example;
import com.fazecast.jSerialComm.*;
import org.example.tools.XmodemReceiver;

import java.io.*;
import java.util.stream.Stream;

public class SerialConnectionTest {
    final String TAFVIEW_PATH = "";
    public void run() throws IOException, InterruptedException {

        // setup
        SerialPort port = SerialPort.getCommPort("COM3");
        port.setBaudRate(SerialPort.FLOW_CONTROL_DISABLED);
        port.setParity(SerialPort.NO_PARITY);
        port.setBaudRate(38400);
        port.setNumStopBits(1);

        // start the file transfer logic
        PrintStream portPrinter = new PrintStream(port.getOutputStream());

        // lancer Enter a couple of times to enter the shell/terminal mode
        for (int i = 0; i < 5; ++i)
            portPrinter.println();

        // login
        portPrinter.println("login admin admin");
        Thread.sleep(1000);

        // send the file
        portPrinter.println("scad");
        Thread.sleep(1000);

        // retrieve the file
        String encodedFilename = "output";
        String decoderFilename = encodedFilename + ".xls";
        InputStream inputStream = new FileInputStream(XmodemReceiver.receive(port, encodedFilename));

        // decode the file
        String[] argv = {encodedFilename, decoderFilename};
        Runtime.getRuntime().exec(TAFVIEW_PATH, argv);
    }

}
