package com.example.question_service.dao;


import com.example.question_service.model.Questions;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface QuestionRepository extends JpaRepository<Questions, Integer> {

    public List<Questions> findByCategory(String category);


    @Query(value = "SELECT q.id FROM questions q WHERE q.category=:category ORDER BY Random() LIMIT :numQ",nativeQuery = true)
    public List<Integer> findRandomQuestionsByCategory(String category, Integer numQ);
}
