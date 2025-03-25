package com.dg.f1fantasyback.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "fantasy_race_point")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FantasyRacePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "race_id", nullable = false)
    private Race race;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @Column(name = "point", nullable = false)
    private Integer point;

}