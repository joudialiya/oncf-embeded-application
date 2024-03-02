package org.example.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
