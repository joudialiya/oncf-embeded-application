package org.example.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity

public class BreakdownInfos {
    @Id
    int id;
    String Code;
    String Designation;
}
