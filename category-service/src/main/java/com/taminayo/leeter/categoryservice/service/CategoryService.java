package com.taminayo.leeter.categoryservice.service;

import com.taminayo.leeter.categoryservice.dto.ProblemRequest;
import com.taminayo.leeter.categoryservice.dto.ProblemResponse;
import com.taminayo.leeter.categoryservice.feign.AuthInterface;
import com.taminayo.leeter.categoryservice.feign.ProblemInterface;
import com.taminayo.leeter.categoryservice.model.Category;
import com.taminayo.leeter.categoryservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProblemInterface problemInterface;
    private final AuthInterface authInterface;

    public ResponseEntity<List<ProblemResponse>> getProblemByType(String token, String type) {
        ResponseEntity<String> userResponse = authInterface.getUsername(token);
        if (userResponse.getStatusCode() == HttpStatus.UNAUTHORIZED) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        List<Category> categories = categoryRepository.findByUsername(userResponse.getBody());
        if (categories.isEmpty()) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
        Category category = null;
        for (Category cand : categories) {
            if (cand.getType().equals(type)) {
                category = cand;
                break;
            }
        }
        if (category == null) return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        List<Integer> problems = category.getProblems();
        List<ProblemResponse> problemResponses = new ArrayList<>();
        for (int problem : problems) {
            ProblemResponse problemResponse = problemInterface.getProblemById(problem).getBody();
            if (problemResponse != null) problemResponses.add(problemResponse);
        }
        return new ResponseEntity<>(problemResponses, HttpStatus.OK);
    }

    public ResponseEntity<String> setProblemByType(String token, String type, List<ProblemRequest> problemRequests) {
        ResponseEntity<String> userResponse = authInterface.getUsername(token);
        if (userResponse.getStatusCode() == HttpStatus.UNAUTHORIZED) return userResponse;
        List<ProblemResponse> problemResponses = problemInterface.getProblemByRequest(problemRequests).getBody();
        if (problemResponses.isEmpty()) return new ResponseEntity<>("invalid format", HttpStatus.BAD_REQUEST);
        List<Integer> problems = new ArrayList<>();
        for (ProblemResponse problemResponse : problemResponses) {
            problems.add(problemResponse.getFrontendQuestionId());
        }
        Category category = Category.builder()
                .type(type)
                .problems(problems)
                .username(userResponse.getBody())
                .build();
        categoryRepository.save(category);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}