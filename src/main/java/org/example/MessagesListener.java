package org.example;

import com.fazecast.jSerialComm.SerialPort;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Le role de cette class c'est de capturer les messages issue du CCU et les enregistrer dans in fichier
 */
public class MessagesListener implements Runnable{
    private static boolean DEBUG = false;
    private SerialPort port;
    private OutputStream outputStream;
    public MessagesListener(SerialPort port, File tmp) throws FileNotFoundException {
        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 2000, 0);
        port.openPort();
        this.port = port;
        this.outputStream = new FileOutputStream(tmp);
    }
    @Override
    public void run()
    {
        try {
            byte[] buffer = new byte[128];
            int read = 0;
            while (true) {
                // receiver
                read = port.readBytes(buffer, 128);
                if (DEBUG)
                    System.out.println("+ Reading: " + read);
                outputStream.write(buffer, 0, read);
                outputStream.flush();
                //System.out.println(new String(new FileInputStream(tmp).readAllBytes()));
                Thread.sleep(2000);
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
