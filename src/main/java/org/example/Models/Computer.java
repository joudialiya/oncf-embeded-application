package org.example.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Entity
public class Computer {
    @Id
    int id;
    String ipAddress = "0.0.0.0";
    @OneToMany(mappedBy = "computer")
    List<RameComputerAssociation> rameComputer;
}
