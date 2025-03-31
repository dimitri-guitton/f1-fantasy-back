package com.dg.f1fantasyback.model.entity.racing;

import com.dg.f1fantasyback.model.entity.fantasy.MarketValue;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "driver")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "profile_picture", nullable = false)
    private String profilePicture;

    @ManyToOne(optional = false)
    @JoinColumn(name = "team_id", nullable = false)
    private Constructor constructor;

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MarketValue> marketValues = new LinkedHashSet<>();

}