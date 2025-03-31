package com.dg.f1fantasyback.model.entity.fantasy;

import com.dg.f1fantasyback.model.entity.AppUser;
import com.dg.f1fantasyback.model.entity.racing.Constructor;
import com.dg.f1fantasyback.model.entity.racing.Driver;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "fantasy_team")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FantasyTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser appUser;

    @Column(name = "label", nullable = false)
    private String label;

    @ManyToMany
    @JoinTable(name = "user_team_drivers",
            joinColumns = @JoinColumn(name = "user_team_id"),
            inverseJoinColumns = @JoinColumn(name = "driver_id"))
    private Set<Driver> drivers = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "user_team_teams",
            joinColumns = @JoinColumn(name = "user_team_id"),
            inverseJoinColumns = @JoinColumn(name = "teams_id"))
    private Set<Constructor> constructors = new LinkedHashSet<>();
}