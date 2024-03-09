import com.fazecast.jSerialComm.SerialPort;
import net.digger.protocol.xymodem.XYModem;
import org.example.tools.DiagnosticFileDecoder;
import org.example.tools.DiagnosticRecord;
import org.example.tools.MyIO;
import org.example.tools.XmodemReceiver;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

public class TestClass {
    @Test
    public void inputStreamPort()
    {
        var com3 = SerialPort.getCommPort("COM3");
        var com4 = SerialPort.getCommPort("COM4");
        com3.openPort();
        com4.openPort();
        com4.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 5000, 2);
        try {
            Thread.sleep(150);
            System.out.println(com4.readBytes(new byte[3], 3));

        }catch (Exception e) {
            e.getStackTrace();
        }

    }
    @Test
    public void testXmodemReceiverCNCVersion() throws IOException, InterruptedException {
        var com4 = SerialPort.getCommPort("COM4");
        com4.openPort();
        XmodemReceiver.receive(com4, "out.txt");
    }
    @Test
    public void testDecoding()
    {
        DiagnosticFileDecoder.decode("output", "out.csv");
    }
}

