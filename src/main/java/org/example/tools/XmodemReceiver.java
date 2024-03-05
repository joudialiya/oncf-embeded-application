package org.example.tools;

import com.fazecast.jSerialComm.SerialPort;

import java.io.*;
import java.util.Arrays;
import java.util.Collection;

/**
 * A Class that encapsulates a basic implementation of a Xmodem receiver,
 * based on jSerialComm [https://fazecast.github.io/jSerialComm/]
 * and following this description [https://en.wikipedia.org/wiki/XMODEM]
 */
public class XmodemReceiver {
    static final int SOH = 1;
    static final int EOT = 4;
    static final int SUB = 26;
    static final int ACK = 6;
    static final int NAK = 21;
    static final int PACKET_LENGTH = 132;
    static final int PAYLOAD_LENGTH = 128;
    static public File receive(SerialPort port, String filename) throws IOException {

        OutputStream outputStream = new FileOutputStream(filename);
        boolean endTransfer = false;

        // open the port if it is down
        if (!port.isOpen())
            port.openPort();

        InputStream portInputStream = port.getInputStream();
        OutputStream portOutputStream = port.getOutputStream();

        // init the transaction
        System.out.println("[Xmodem receiver] init the transaction");
        portOutputStream.write(NAK);

        while (!endTransfer){
            // get the packet
            System.out.println("get the packet");
            byte[] packetData = portInputStream.readNBytes(PACKET_LENGTH);

            byte[] payload = Arrays.copyOfRange(packetData, 2, packetData.length);

            // check for end of transfer
            if (portInputStream.available() > 0 && portInputStream.read() == EOT)
                endTransfer = true;
            System.out.println("check for end of transfer: " + endTransfer);

            // get the checksum from the packet
            int packetChecksum = packetData[PACKET_LENGTH - 1];

            int calculatedChecksum = calculateChecksum(payload);

            System.out.println("packet Checksum: " + packetChecksum);
            System.out.println("calculated Checksum: " + calculatedChecksum);

            if (packetChecksum == calculatedChecksum) {

                if (!endTransfer)
                    outputStream.write(payload);
                else {
                    // if we reached the end of transfer we check for padding
                    int endByteIndex = 0;
                    for (; endByteIndex < payload.length && payload[endByteIndex] != SUB; ++endByteIndex);
                    outputStream.write(payload, 0, endByteIndex);
                }
                portOutputStream.write(ACK);
            }
            else
                portOutputStream.write(NAK);
        }
        System.out.println("return");
        return new File(filename);
    }
    static private int calculateChecksum(byte[] payload)
    {
        int checksum = 0;
        for (byte b: payload)
            checksum += b;
        checksum %= 256;
        return checksum;
    }
}
