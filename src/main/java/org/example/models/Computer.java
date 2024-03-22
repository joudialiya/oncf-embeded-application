package org.example.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Data
@Entity
public class Computer {
    @Id
    @GeneratedValue
    int id;
    String ipAddress = "0.0.0.0";
    @OneToMany(mappedBy = "computer")
    List<RameComputerAssociation> rameComputer;
}
