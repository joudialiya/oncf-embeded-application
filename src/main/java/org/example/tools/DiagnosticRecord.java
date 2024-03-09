package org.example.tools;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Arrays;

public class DiagnosticRecord {
    private byte[] bytes;

    public DiagnosticRecord(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getCode()
    {
        return String.format("%02x%02x", bytes[4], bytes[5]);
    }
    public int getVelocity()
    {
        int result = bytes[6] & 0xff;
        result <<= 8;
        result += bytes[7] & 0xff;
        return result;
    }
    public int getLineTension()
    {
        int result = bytes[8] & 0xff;
        result <<= 8;
        result += bytes[9] & 0xff;
        return result;
    }
    public int getVehicleNumber()
    {
        return bytes[3] & 0xff;
    }
    public String getDateTime()
    {
//        System.out.println(bytes[15] & 0xff);
//        System.out.println(bytes[14] & 0xff);
//        System.out.println(bytes[13] & 0xff);
//        System.out.println(bytes[12] & 0xff);
//        System.out.println(bytes[11] & 0xff);
//        System.out.println(bytes[10] & 0xff);
        return getDate() + " " + getTime();
    }
    public String getDate()
    {
        return (bytes[15] & 0xff) + "/" + (bytes[14] & 0xff) + "/" + (bytes[13] & 0xff);
    }
    public String getTime()
    {
        return (bytes[10] & 0xff) + ":" + (bytes[11] & 0xff) + ":" + (bytes[12] & 0xff);
    }
    public LocalDateTime getDateTimeLocalDateTimeObject()
    {
        System.out.println(bytes[15] & 0xff);
        System.out.println(bytes[14] & 0xff);
        System.out.println(bytes[13] & 0xff);
        System.out.println(bytes[12] & 0xff);
        System.out.println(bytes[11] & 0xff);
        System.out.println(bytes[10] & 0xff);
        return LocalDateTime.of(
                (bytes[15] & 0xff) + 2000,
                (bytes[14] & 0xff),
                (bytes[13] & 0xff),
                (bytes[10] & 0xff),
                (bytes[11] & 0xff),
                (bytes[12] & 0xff));
    }

}
