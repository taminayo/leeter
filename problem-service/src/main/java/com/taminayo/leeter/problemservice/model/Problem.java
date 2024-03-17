package com.taminayo.leeter.problemservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Problem {

    @Id
    @Column(unique = true)
    private int frontendQuestionId;
    @Column(unique = true)
    private String titleSlug;
}
