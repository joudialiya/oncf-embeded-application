package org.example;
import com.fazecast.jSerialComm.*;
import org.example.tools.XmodemReceiver;

import java.io.*;
import java.util.stream.Stream;

public class SerialConnectionTest {
    final String TAFVIEW_PACH = "";
    public void run() throws IOException {

        SerialPort port = SerialPort.getCommPort("COM3");
//        port.setBaudRate(SerialPort.FLOW_CONTROL_DISABLED);
//        port.setParity(SerialPort.NO_PARITY);
//        port.setBaudRate(38400);
//        port.setNumStopBits(1);

        // start the file transfer logic

        // retrieve the file
        String encodedFilename = "output";
        String decoderFilename = encodedFilename + ".xls";
        InputStream inputStream = new FileInputStream(XmodemReceiver.receive(port, encodedFilename));

        // decode the file
        String[] argv = {encodedFilename, decoderFilename};
        Runtime.getRuntime().exec(TAFVIEW_PACH, argv);
    }

}
