package org.example.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class RameComputerAssociation {
    @Id
    private int id;
    @ManyToOne
    @JoinColumn(name = "idComputer")
    private Computer computer;
    @OneToOne
    @JoinColumn(name = "idRame")
    private Rame rame;
}
