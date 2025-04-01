package com.dg.f1fantasyback.model.entity.racing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link GrandPrix}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrandPrixDto implements Serializable {
    private Integer id;
    private String label;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
}