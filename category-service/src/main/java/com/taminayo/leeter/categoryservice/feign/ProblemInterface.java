package com.taminayo.leeter.categoryservice.feign;

import com.taminayo.leeter.categoryservice.dto.ProblemRequest;
import com.taminayo.leeter.categoryservice.dto.ProblemResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("PROBLEM-SERVICE")
public interface ProblemInterface {

    @PostMapping("problem/get")
    public ResponseEntity<List<ProblemResponse>> getProblemByRequest(@RequestBody List<ProblemRequest> problemRequests);

    @GetMapping("problem/get/{frontendQuestionId}")
    public ResponseEntity<ProblemResponse> getProblemById(@PathVariable("frontendQuestionId") int frontendQuestionId);

}