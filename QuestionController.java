package com.example.question_service.controller;


import com.example.question_service.dao.QuestionRepository;
import com.example.question_service.model.QuestionWrapper;
import com.example.question_service.model.Questions;
import com.example.question_service.model.Response;
import com.example.question_service.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionRepository questionRepository;
    @GetMapping("hello")
    public String Hello(){
        return "Hello from Spring";
    }

    @GetMapping("allquestions")
    public ResponseEntity<List<Questions>> showAllQuestions(){
        return questionService.getAllQuestions();
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Questions>> getQuestionByCategory(@PathVariable String category){
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Questions question){
        return questionService.addQuestion(question);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Questions id){
        return questionService.deleteQuestion(id);
    }
    //generate questions
    @GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz
            (@RequestParam String categoryName,@RequestParam Integer numQuestions){
        List<Integer> questions= questionRepository.findRandomQuestionsByCategory(categoryName, numQuestions);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }
    //provide all questions without answer so we will return question wrapper
    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getAllQuestionsById(@RequestBody List<Integer> questionId){
        return questionService.getQuestionsById(questionId);
    }

    //this is for returning a score to the quiz service
    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
        return questionService.getScore(responses);
    }

}
