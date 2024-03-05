package org.example;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, IOException, InterruptedException {
        new SerialConnectionTest().run();
    }
}