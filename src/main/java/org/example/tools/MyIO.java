package org.example.tools;

import com.fazecast.jSerialComm.SerialPort;
import net.digger.protocol.xymodem.Download;
import net.digger.protocol.xymodem.IOHandler;
import net.digger.protocol.xymodem.UserCancelException;

import java.io.IOException;

public class MyIO implements IOHandler {

    SerialPort port;
    public MyIO (SerialPort port)
    {
        this.port = port;
    }
    @Override
    public Byte read(int i) throws UserCancelException {
        byte[] buffer = new byte[1];
        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, i, 0);
        int nbr = port.readBytes(buffer, 1);
        System.out.println("R: " + nbr);
        return buffer[0];
    }
    @Override
    public void write(char c) {

        int nbr = port.writeBytes(new byte[]{(byte) c}, 1);
        System.out.println("W: "+ nbr);
    }
    @Override
    public void log(String s) {
        System.out.println(s);
    }
    @Override
    public void progress(long l, long l1) {

    }
    @Override
    public void received(Download download) {

    }
}
