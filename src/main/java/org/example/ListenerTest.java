package org.example;

import com.fazecast.jSerialComm.SerialPort;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ListenerTest{
    public void run() throws IOException {
        // the first thread is for testing purposes, its role is to simulate the CCU sending messages
        new Thread(new Runnable() {
            @Override
            public void run() {
                SerialPort com3 = SerialPort.getCommPort("com3");
                com3.openPort();
                String fileName = "c:/users/lagzal/desktop/breakdown.txt";
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(fileName));
                    boolean EOF = false;
                    while(!EOF)
                    {
                        String line = reader.readLine() + '\n';
                        if (line == null)
                            EOF = true;
                        com3.writeBytes(line.getBytes(), line.length());
                        Thread.sleep(2000);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        File tmp = File.createTempFile("tmp", "log");
        SerialPort com4 = SerialPort.getCommPort("com4");

        // the second thread capture the messages and save them in a tmp file
        new Thread(new MessagesListener(com4, tmp)).start();
        // the third thread parse the messages from the file save them in the database
        new Thread(new MessagesParser(tmp)).start();
    }
}
