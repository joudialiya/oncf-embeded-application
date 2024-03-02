package org.example.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Entity

public class BreakdownInfos {
    @Id
    int id;
    String Code;
    String Designation;
}
