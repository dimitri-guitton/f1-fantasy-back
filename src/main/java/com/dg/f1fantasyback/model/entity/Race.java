package com.dg.f1fantasyback.model.entity;

import com.dg.f1fantasyback.model.enums.RaceTypeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "race")
public class Race {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "grand_prix_id", nullable = false)
    private GrandPrix grandPrix;

    @Column(name = "label", nullable = false)
    private String label;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_at", nullable = false)
    private Date startAt;

    @Enumerated
    @Column(name = "type", nullable = false)
    private RaceTypeEnum type;

}