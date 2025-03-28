package com.dg.f1fantasyback.model.entity;

import com.dg.f1fantasyback.model.enums.RaceTypeEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Table(name = "race")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
    private LocalDateTime startAt;

    @Enumerated
    @Column(name = "type", nullable = false)
    private RaceTypeEnum type;

    @Column(name = "is_calculated", nullable = false)
    @ColumnDefault("false")
    private Boolean isCalculated = false;
}