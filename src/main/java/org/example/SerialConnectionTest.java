package org.example;
import com.fazecast.jSerialComm.*;
import org.example.tools.XmodemReceiver;

import java.io.*;
import java.util.Arrays;
import java.util.stream.Stream;

public class SerialConnectionTest {
    final String TAFVIEW_PATH = "";
    public void run() throws IOException, InterruptedException {

        // setup
        SerialPort port = SerialPort.getCommPort("COM5");
        port.setBaudRate(SerialPort.FLOW_CONTROL_DISABLED);
        port.setParity(SerialPort.NO_PARITY);
        port.setBaudRate(38400);
        port.setNumStopBits(1);
        port.setNumDataBits(8);
        if (port.isOpen())
            port.closePort();
        port.openPort();
        byte[] bytes = new byte[999];

        // start the file transfer logic
        PrintStream portPrinter = new PrintStream(port.getOutputStream());
        portPrinter.println();
        Thread.sleep(500);
        System.out.println(new String(Arrays.copyOfRange(bytes, 0, port.readBytes(bytes, 999))));
        Thread.sleep(500);
        port.writeBytes(new byte[]{'C'}, 1);
        System.out.println(port.bytesAwaitingWrite());
        Thread.sleep(500);
        System.out.println(new String(Arrays.copyOfRange(bytes, 0, port.readBytes(bytes, 999))));

        System.out.println("+ login");
        portPrinter.println("login admin admin");
        Thread.sleep(500);
        System.out.println(new String(Arrays.copyOfRange(bytes, 0, port.readBytes(bytes, 999))));
        Thread.sleep(500);
        System.out.println("+ scad");
        portPrinter.println("scad");

            Thread.sleep(500);
        System.out.println(new String(Arrays.copyOfRange(bytes, 0, port.readBytes(bytes, 999))));
        XmodemReceiver.receive(port, "output");

    }

}
