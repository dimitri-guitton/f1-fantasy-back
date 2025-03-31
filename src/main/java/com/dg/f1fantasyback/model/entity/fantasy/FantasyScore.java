package com.dg.f1fantasyback.model.entity.fantasy;

import com.dg.f1fantasyback.model.entity.racing.Constructor;
import com.dg.f1fantasyback.model.entity.racing.Driver;
import com.dg.f1fantasyback.model.entity.racing.Event;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "fantasy_score")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FantasyScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "race_id", nullable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Constructor constructor;

    @Column(name = "point", nullable = false)
    private Integer point;

}