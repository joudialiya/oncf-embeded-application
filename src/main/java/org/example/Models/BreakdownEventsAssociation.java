package org.example.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

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
