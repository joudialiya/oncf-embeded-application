package org.example.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity

public class Rame {
    @Id
    private int id;
    private String name;
    @OneToOne(mappedBy = "rame", fetch = FetchType.EAGER)
    private RameComputerAssociation computerAssociation;
}
