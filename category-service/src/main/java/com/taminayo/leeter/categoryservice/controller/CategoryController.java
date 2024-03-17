package com.taminayo.leeter.categoryservice.controller;

import com.taminayo.leeter.categoryservice.dto.ProblemRequest;
import com.taminayo.leeter.categoryservice.dto.ProblemResponse;
import com.taminayo.leeter.categoryservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("get/{type}")
    public ResponseEntity<List<ProblemResponse>> getProblemByType(@RequestHeader("Authorization") String token, @PathVariable("type") String type) {
        return categoryService.getProblemByType(token, type);
    }

    @PostMapping("{type}")
    public ResponseEntity<String> setProblemByType(@RequestHeader("Authorization") String token, @PathVariable("type") String type, @RequestBody List<ProblemRequest> problemRequests) {
        return categoryService.setProblemByType(token, type, problemRequests);
    }
}
