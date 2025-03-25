package com.dg.f1fantasyback.model.entity;

import jakarta.persistence.*;
import lombok.*;

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
    private Team team;
}