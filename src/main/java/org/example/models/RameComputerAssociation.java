package org.example.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Check;

@Data
@Entity
@Check(constraints = "placement in ('M', 'MH')")
public class RameComputerAssociation {
    @Id
    private int id;
    @ManyToOne
    @JoinColumn(name = "idComputer")
    private Computer computer;
    @OneToOne
    @JoinColumn(name = "idRame")
    private Rame rame;
    private String placement;
}
