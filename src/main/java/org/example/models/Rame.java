package org.example.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity

public class Rame {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    @OneToOne(mappedBy = "rame", fetch = FetchType.EAGER)
    private RameComputerAssociation computerAssociation;
}
