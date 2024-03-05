package org.example.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class BreakdownEventsAssociation {
    @Id
    int id;
    @ManyToOne
    @JoinColumn(name = "idRame")
    Rame rame;
    @ManyToOne
    @JoinColumn(name = "idBreakdown")
    BreakdownInfos breakdownInfos;
    LocalDateTime date;
    float velocity;
    int tension;
}
