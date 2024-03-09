package org.example.tools;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.Writer;

/* this class recreate the function of taf view, it provides a static method that
takes the encoded file and decode it into csv file */
public class DiagnosticFileDecoder {
    private static final boolean DEBUG = true;
    public static void decode(String filename, String outputFilename)
    {
        try {
            InputStream in = new FileInputStream(filename);
            PrintStream out = new PrintStream(outputFilename);
            boolean endOfFile = false;
            int recordNumber = 0;

            while(true)
            {
                if (DEBUG)
                    System.out.println("+ Record number: " + recordNumber);
                byte[] record = new byte[16];
                int localFFCount = 0;
                for (int  i = 0; i < 16; ++i) {
                    int b = in.read();
                    if (DEBUG)
                        System.out.print(String.format("%02x", b & 0xff));
                    if ((b & 0xff) == 0xff)
                    {
                        localFFCount++;
                    }
                    if (b == -1) {
                        if (DEBUG)
                            System.out.println("\n+ End of file");
                        endOfFile = true;
                        break;
                    }
                    else
                        record[i] = (byte) (b & 0xff);
                }
                recordNumber++;
                if (DEBUG)
                    System.out.println();
                if (localFFCount >= 15)
                    break;
                if (endOfFile)
                    break;

                DiagnosticRecord diagnosticRecord = new DiagnosticRecord(record);
                if (DEBUG)
                    System.out.println("+ Write vehicle number");
                out.print(diagnosticRecord.getVehicleNumber());
                out.print(";");
                if (DEBUG)
                    System.out.println("+ Write code");
                out.print(diagnosticRecord.getCode());
                out.print(";");
                if (DEBUG)
                    System.out.println("+ Write date");
                out.print(diagnosticRecord.getDate());
                out.print(";");
                if (DEBUG)
                    System.out.println("+ Write time");
                out.print(diagnosticRecord.getTime());
                out.print(";");
                if (DEBUG)
                    System.out.println("+ Write velocity");
                out.print(diagnosticRecord.getVelocity());
                out.print(";");
                if (DEBUG)
                    System.out.println("+ Write line tension");
                out.print(diagnosticRecord.getLineTension());
                out.print('\n');
                out.flush();
            }
        }
        catch (Exception e)
        {
            e.getStackTrace();
        }
    }
}
