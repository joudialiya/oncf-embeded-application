package org.example.tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationManager {
    public static Properties properties;
    public static void init(String configFile) {
        properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(configFile);
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}