package com.taminayo.leeter.problemservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemRequest {

    private int frontendQuestionId;
    private String titleSlug;
}
