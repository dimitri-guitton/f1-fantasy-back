package com.dg.f1fantasyback.model.entity.fantasy;

import com.dg.f1fantasyback.model.entity.AppUser;
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
    private AppUser user;

    @Column(name = "label", nullable = false)
    private String label;

    @OneToMany(mappedBy = "fantasyTeam", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FantasyTeamComposition> fantasyTeamCompositions = new LinkedHashSet<>();
}