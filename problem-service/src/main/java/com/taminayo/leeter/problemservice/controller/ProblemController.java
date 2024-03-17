package com.taminayo.leeter.problemservice.controller;

import com.taminayo.leeter.problemservice.dto.ProblemRequest;
import com.taminayo.leeter.problemservice.dto.ProblemResponse;
import com.taminayo.leeter.problemservice.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("problem")
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemService problemService;

    @PostMapping
    public ResponseEntity<String> createProblem(@RequestBody List<ProblemRequest> problemRequests) {
        return problemService.createProblem(problemRequests);
    }

    @GetMapping
    public ResponseEntity<List<ProblemResponse>> getAllProblems() {
        return problemService.getAllProblems();
    }

    @PostMapping("get")
    public ResponseEntity<List<ProblemResponse>> getProblemsByRequest(@RequestBody List<ProblemRequest> problemRequests) {
        return problemService.getProblemsByRequest(problemRequests);
    }

    @GetMapping("get/{frontendQuestionId}")
    public ResponseEntity<ProblemResponse> getProblemById(@PathVariable("frontendQuestionId") int frontendQuestionId) {
        return problemService.getProblemById(frontendQuestionId);
    }

}
