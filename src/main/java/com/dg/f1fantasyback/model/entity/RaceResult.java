package com.dg.f1fantasyback.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "race_result")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RaceResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @ManyToOne(optional = false)
    @JoinColumn(name = "race_id", nullable = false)
    private Race race;

    @Column(name = "position", nullable = false)
    private Integer position;

    @Column(name = "dnf", nullable = false)
    private Boolean dnf = false;

}