package org.example.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class BreakdownEventsAssociation {
    @Id
    @GeneratedValue
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
