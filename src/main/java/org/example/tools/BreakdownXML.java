package org.example.tools;

import lombok.Data;


/**
 * Cette class represente une pannes dans le fichier Resource.xml
 */
@Data
public class BreakdownXML {
    private String device;
    private String code;
    private String description;
    private String pdm;
    private String pdo;
}
