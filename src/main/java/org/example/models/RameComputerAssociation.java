package org.example.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Check;

@Data
@Entity
public class RameComputerAssociation {
    @Id
    @GeneratedValue
    private int id;
    @ManyToOne
    @JoinColumn(name = "idComputer")
    private Computer computer;
    @OneToOne
    @JoinColumn(name = "idRame")
    private Rame rame;
    private String placement;
}