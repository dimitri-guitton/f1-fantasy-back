package com.dg.f1fantasyback.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "grand_prix")
public class GrandPrix {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "label", nullable = false)
    private String label;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_at", nullable = false)
    private Date startAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_at", nullable = false)
    private Date endAt;

    @OneToMany(mappedBy = "grandPrix", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Race> races = new LinkedHashSet<>();

}