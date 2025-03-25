package com.dg.f1fantasyback.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "race_result")
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