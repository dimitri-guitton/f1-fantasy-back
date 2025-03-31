package com.dg.f1fantasyback.model.entity.racing;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "grand_prix")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GrandPrix {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "label", nullable = false)
    private String label;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_at", nullable = false)
    private LocalDateTime endAt;

    @OneToMany(mappedBy = "grandPrix", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Event> events = new LinkedHashSet<>();

    public void addRace(Event event) {
        events.add(event);
        event.setGrandPrix(this);
    }

    public void removeRace(Event event) {
        events.remove(event);
        event.setGrandPrix(null);
    }

}