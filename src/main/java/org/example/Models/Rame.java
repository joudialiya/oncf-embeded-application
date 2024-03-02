package org.example.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Entity

public class Rame {
    @Id
    private int id;
    private String name;
    @OneToOne(mappedBy = "rame", fetch = FetchType.EAGER)
    private RameComputerAssociation computerAssociation;
}
