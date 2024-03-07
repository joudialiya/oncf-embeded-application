package org.example;



import com.fazecast.jSerialComm.SerialPort;
import net.digger.protocol.xymodem.XYModem;
import org.example.tools.MyIO;
import org.example.tools.XmodemReceiver;
import org.hibernate.engine.jdbc.ReaderInputStream;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws SQLException, IOException, InterruptedException {
        new SerialConnectionTest().run();
//        SerialPort port = SerialPort.getCommPort("COM3");
//        port.setBaudRate(SerialPort.FLOW_CONTROL_DISABLED);
//        port.setParity(SerialPort.NO_PARITY);
//        port.setBaudRate(38400);
//        port.setNumStopBits(1);
//        port.setNumDataBits(8);
//        port.openPort();
//        System.out.println(port.isOpen());
//        while(true) {
//            if (port.bytesAvailable() > 0)
//                System.out.println(port.getInputStream().read());
//            Thread.sleep(120);
//            System.out.println("skip");
//        }
//        PrintStream portPrinter = new PrintStream(port.getOutputStream());
//        InputStream portInput  = port.getInputStream();
//        byte[] bytes = new byte[999];
//        XmodemReceiver.receive(port, "out.txt");

//        portPrinter.print("\n");
//        Thread.sleep(500);
//        nbr = port.readBytes(bytes, 900);
//        System.out.println("+ Number of bytes read: " + nbr);
//        System.out.println(new String(Arrays.copyOfRange(bytes, 0, nbr), StandardCharsets.UTF_8));
//
//        portPrinter.print("login admin admin\n");
//        Thread.sleep(500);
//        nbr = port.readBytes(bytes, 900);
//        System.out.println("+ Number of bytes read: " + nbr);
//        System.out.println(new String(Arrays.copyOfRange(bytes, 0, nbr), StandardCharsets.UTF_8));
//
//        portPrinter.print("scad\n");
//        Thread.sleep(500);
//        nbr = port.readBytes(bytes, 900);
//        System.out.println("+ Number of bytes read: " + nbr);
//        System.out.println(new String(Arrays.copyOfRange(bytes, 0, nbr), StandardCharsets.UTF_8));
        //new XYModem(new MyIO(port)).download();

    }
}