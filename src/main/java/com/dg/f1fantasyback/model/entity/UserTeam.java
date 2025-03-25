package com.dg.f1fantasyback.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_team")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "label", nullable = false)
    private String label;

}