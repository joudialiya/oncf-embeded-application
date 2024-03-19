package org.example.tools;

import com.fazecast.jSerialComm.SerialPort;
import net.digger.util.crc.CRC;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;


/**
 * A Class that encapsulates a basic implementation of a Xmodem CRC receiver,
 * based on jSerialComm [https://fazecast.github.io/jSerialComm/]
 * and following this description [https://en.wikipedia.org/wiki/XMODEM]
 */
public class XmodemReceiver {
    public static int INIT_MAX_ATTEMPTS = 11;
    public static boolean DEBUG = true;
    public static final int SOH = 1;
    public static final int EOT = 4;
    public static final int EOF = EOT;
    public static final int SUB = 26;
    public static final int ACK = 6;
    public static final int NAK = 21;
    public static final int XMODEM_PACKET_LENGTH = 132;
    public static final int CNC_XMODEM_PACKET_LENGTH = 133;
    public static final int PAYLOAD_LENGTH = 128;
    public static final int HEADER_LENGTH = 3;
    static public File receive(SerialPort port, String filename) throws IOException, InterruptedException {

        OutputStream outputStream = new FileOutputStream(filename);
        boolean isTransmissionActive = true;
        boolean isFirstPacket = true;
        int errCount = 0;
        int attemptCount = 0;
        int blockNumber = 1;
        byte[] packetData = new byte[CNC_XMODEM_PACKET_LENGTH];

        // open the port if it is down
        if (!port.isOpen())
            port.openPort();

        //port.readBytes(new byte[999], 999);
        // init the transaction
        if (DEBUG)
            System.out.println("[Xmodem receiver] init the transaction");

        if (DEBUG)
            System.out.println("Sleep");
        Thread.sleep(1500);

        while (isTransmissionActive){
            if (isFirstPacket)
            {
                if (DEBUG)
                    System.out.println("Trying to init");
                while (packetData[0] != SOH && attemptCount < INIT_MAX_ATTEMPTS)
                {
                    if (DEBUG)
                        System.out.println("Attempts so far: " + attemptCount);
                    port.writeBytes(new byte[]{'C'}, 1);
                    int nbr = port.readBytes(packetData, CNC_XMODEM_PACKET_LENGTH);
                    if (DEBUG)
                    {
                        System.out.println("Number of bytes: " + nbr);
                        System.out.println("is the header right: " + (packetData[0] == SOH));
                    }
                    ++attemptCount;
                }
                if (attemptCount >= INIT_MAX_ATTEMPTS)
                {
                    if (DEBUG)
                        System.out.println("Can't Receive first packet");
                    isTransmissionActive = false;
                    continue;
                }
                isFirstPacket = false;
                attemptCount = 0;
            }
            else {
                if (DEBUG)
                    System.out.println("+ Get the packet: " + blockNumber);
                int nbr = port.readBytes(packetData, CNC_XMODEM_PACKET_LENGTH);
                if (DEBUG)
                    System.out.println("Packet size: " + nbr);

                if (packetData[0] == EOT) {
                    if (DEBUG)
                        System.out.println("End of transmission");
                    isTransmissionActive = false;
                    port.writeBytes(new byte[]{ACK}, 1);
                    continue;
                }
            }
            byte[] payload = Arrays.copyOfRange(packetData, 3, 3 + PAYLOAD_LENGTH);
//            if (DEBUG)
//                System.out.println(new String(payload));

            // get the checksum from the packet
            int packetCNC = packetData[CNC_XMODEM_PACKET_LENGTH - 2] & 0xff;
            packetCNC = (packetCNC << 8) + (packetData[CNC_XMODEM_PACKET_LENGTH - 1] & 0xff);

            int calculatedCNC = (int) CRC.calculate(CRC.CRC16_CCITT_XModem, payload);

            if (DEBUG) {
                System.out.println("packet CNC: " + packetCNC);
                System.out.println("calculated CNC: " + calculatedCNC);
            }
            if (true) {
                if (DEBUG)
                    System.out.println("write data out to the file");
                // if we reached the end of transfer we check for padding
                int endByteIndex = 0;
                for (; (endByteIndex < payload.length) && (payload[endByteIndex] != SUB); ++endByteIndex) ;
                outputStream.write(payload, 0, endByteIndex);

                port.writeBytes(new byte[]{ACK}, 1);
                ++blockNumber;
            } else
                port.writeBytes(new byte[]{NAK}, 1);
        }
        if (DEBUG)
            System.out.println("return");
        return new File(filename);
    }
    static public void receiveV2(SerialPort port, String output) throws IOException {
        int blockNumber = 1;
        int errorCount = 0;
        boolean endOfTransmission = false;
        boolean initPhase = true;
        byte[] packet = new byte[CNC_XMODEM_PACKET_LENGTH];
        byte[] payload = null;
        OutputStream outputStream = new FileOutputStream(output);
        while (!endOfTransmission && errorCount < 10)
        {
            boolean errorFound = false;
            if (initPhase)
            {
                port.writeBytes(new byte[]{'C'}, 1);
            }
            if (DEBUG) {
                System.out.println("+ Receiving the packet number: " + blockNumber);
                System.out.println("Initialise phase: " + initPhase);
                System.out.println("Error count: " + errorCount);
            }
            int nbr = port.readBytes(packet, CNC_XMODEM_PACKET_LENGTH);
            if (nbr > 0)
                initPhase = false;
            if (DEBUG)
                System.out.println("Packet length: " + nbr);
            if (packet[0] == EOT) {
                endOfTransmission = true;
                if (DEBUG)
                    System.out.println("End of transmission");
            }
            else
            {
                if ((packet[0] & 0xff) != SOH) {
                    if (DEBUG)
                        System.out.println("Wrong header: " + packet[0] + " ref: " + SOH);
                    errorFound = true;
                }
                if (!errorFound) {

                    payload = Arrays.copyOfRange(packet, 3, 3 + PAYLOAD_LENGTH);
                    int packetCNC = packet[CNC_XMODEM_PACKET_LENGTH - 2] & 0xff;

                    packetCNC = (packetCNC << 8) + (packet[CNC_XMODEM_PACKET_LENGTH - 1] & 0xff);

                    int calculatedCNC = (int) CRC.calculate(CRC.CRC16_CCITT_XModem, payload);

                    if (DEBUG) {
                        System.out.println("Packet CNC: " + packetCNC);
                        System.out.println("Calculated CNC: " + calculatedCNC);
                    }

                    if (calculatedCNC != packetCNC) {
                        errorFound = true;
                    }
                }
                if (!errorFound) {
                    if (DEBUG)
                        System.out.println("Write data out to the file");
                    // if we reached the end of transfer we check for padding
                    int endByteIndex = 0;
                    for (; (endByteIndex < payload.length) && (payload[endByteIndex] != SUB); ++endByteIndex) ;
                    outputStream.write(payload, 0, endByteIndex);
                }
            }
            if (errorFound) {
                ++errorCount;
                if (DEBUG)
                    System.out.println("NAK");
                port.writeBytes(new byte[]{NAK}, 1);
            }
            else {
                errorCount = 0;
                ++blockNumber;
                if (DEBUG)
                    System.out.println("ACK");
                port.writeBytes(new byte[]{ACK}, 1);
            }
        }
    }
    static private int calculateChecksum(byte[] payload)
    {
        int checksum = 0;
        for (byte b: payload) {
            checksum += b;
        }
        checksum %= 256;
        return checksum;
    }
    static private int calculateCNC(byte[] payload)
    {
        final int CNCPrimNumber = 65536;
        int payloadValue = 0;
        for(var b: payload)
            payloadValue = (payloadValue << 8) + (b & 0xff);
        payloadValue &= 0xffff;
        return (payloadValue % 65536) & 0xffff;
    }
}
