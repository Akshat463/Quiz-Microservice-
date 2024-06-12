package com.example.question_service.service;

import com.example.question_service.dao.QuestionRepository;
import com.example.question_service.model.QuestionWrapper;
import com.example.question_service.model.Questions;
import com.example.question_service.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;
    public ResponseEntity<List<Questions>> getAllQuestions() {

        try{
            return new ResponseEntity<List<Questions>>(questionRepository.findAll(),HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Questions>> getQuestionsByCategory(String category) {
        try{
            return new ResponseEntity<>(questionRepository.findByCategory(category),HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Questions question) {
        try{
            questionRepository.save(question);
            return new ResponseEntity<>("Success",HttpStatus.CREATED);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Error Occurred" ,HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteQuestion(Questions question) {
        try{
            questionRepository.delete(question);
            return new ResponseEntity<>("Success",HttpStatus.CREATED);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Error Occurred" ,HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsById(List<Integer> questionsId) {
        List<QuestionWrapper> wrapper =new ArrayList<>();
        List<Questions> questions=new ArrayList<>();
        for(Integer id: questionsId){
            questions.add(questionRepository.findById(id).get());
        }
        for(Questions question: questions ){
            QuestionWrapper ques=new QuestionWrapper();
            ques.setId(question.getId());
            ques.setOption1(question.getOption1());
            ques.setOption2(question.getOption2());
            ques.setOption3(question.getOption3());
            ques.setOption4(question.getOption4());
            wrapper.add(ques);
        }

        return new ResponseEntity<>(wrapper,HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {

        int right=0;
        for(Response response: responses) {
            Questions question=questionRepository.findById(response.getId()).get();
            if (response.getResponse().equals(question.getRight_option()))
                right++;
        }

        return new ResponseEntity<>(right,HttpStatus.OK);
    }
}
