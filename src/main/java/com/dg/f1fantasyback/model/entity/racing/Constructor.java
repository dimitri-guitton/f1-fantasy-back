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

    @Column(name = "label", nullable = false, unique = true)
    private String label;

    @Column(name = "logo")
    private String logo;

    @OneToMany(mappedBy = "constructor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Driver> drivers = new LinkedHashSet<>();

    @OneToMany(mappedBy = "constructor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MarketValue> marketValues = new LinkedHashSet<>();
    @Column(name = "full_logo")
    private String fullLogo;

    public void addDriver(Driver driver) {
        drivers.add(driver);
        driver.setConstructor(this);
    }

    public void removeDriver(Driver driver) {
        drivers.remove(driver);
        driver.setConstructor(null);
    }

}