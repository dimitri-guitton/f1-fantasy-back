package com.dg.f1fantasyback.model.entity.racing;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "event_result")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @ManyToOne(optional = false)
    @JoinColumn(name = "race_id", nullable = false)
    private Event event;

    @Column(name = "start_position")
    private Integer startPosition;

    @Column(name = "end_position", nullable = false)
    private Integer endPosition;

    @Column(name = "dnf", nullable = false)
    private Boolean dnf = false;

    @Column(name = "fastest_lap", nullable = false)
    @ColumnDefault("false")
    private Boolean fastestLap = false;

    @Column(name = "driver_of_the_day", nullable = false)
    @ColumnDefault("false")
    private Boolean driverOfTheDay;

    @Column(name = "nb_overtakes", nullable = false)
    @ColumnDefault("0")
    private Integer nbOvertakes;


}