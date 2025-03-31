package com.dg.f1fantasyback.model.entity.racing;

import com.dg.f1fantasyback.model.entity.fantasy.MarketValue;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "constructor")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Constructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "logo")
    private String logo;

    @Column(name = "full_logo")
    private String fullLogo;

    @OneToMany(mappedBy = "constructor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Driver> drivers = new LinkedHashSet<>();

    @OneToMany(mappedBy = "constructor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MarketValue> marketValues = new LinkedHashSet<>();
}