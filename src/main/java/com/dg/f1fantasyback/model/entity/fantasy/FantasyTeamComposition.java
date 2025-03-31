package com.dg.f1fantasyback.model.entity.fantasy;

import com.dg.f1fantasyback.model.entity.racing.Constructor;
import com.dg.f1fantasyback.model.entity.racing.Driver;
import com.dg.f1fantasyback.model.entity.racing.GrandPrix;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "fantasy_team_composition")
public class FantasyTeamComposition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "grand_prix_id")
    private GrandPrix grandPrix;

    @ManyToOne
    @JoinColumn(name = "fantasy_team_id")
    private FantasyTeam fantasyTeam;

    @ManyToMany
    @JoinTable(name = "fantasy_team_composition_constructors",
            joinColumns = @JoinColumn(name = "fantasy_team_composition_id"),
            inverseJoinColumns = @JoinColumn(name = "constructor_id"))
    private Set<Constructor> constructors = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "fantasy_team_composition_drivers",
            joinColumns = @JoinColumn(name = "fantasy_team_composition_id"),
            inverseJoinColumns = @JoinColumn(name = "driver_id"))
    private Set<Driver> drivers = new LinkedHashSet<>();

}