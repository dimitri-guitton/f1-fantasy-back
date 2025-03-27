package com.dg.f1fantasyback.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "team")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "label", nullable = false, unique = true)
    private String label;

    @Column(name = "logo")
    private String logo;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Driver> drivers = new LinkedHashSet<>();

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Price> prices = new LinkedHashSet<>();

    public void addDriver(Driver driver) {
        drivers.add(driver);
        driver.setTeam(this);
    }

    public void removeDriver(Driver driver) {
        drivers.remove(driver);
        driver.setTeam(null);
    }

    @Column(name = "full_logo")
    private String fullLogo;

}