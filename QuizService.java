package com.example.quizService.service;


import com.example.quizService.Feign.FeignInterface;
import com.example.quizService.dao.QuizRepository;
import com.example.quizService.model.QuestionWrapper;
import com.example.quizService.model.Quiz;
import com.example.quizService.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Service
public class QuizService {

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    FeignInterface feignInterface;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title){

        List<Integer> questions=feignInterface.getQuestionsForQuiz(category,numQ).getBody();

        Quiz quiz =new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionsIds(questions);
        quizRepository.save(quiz);
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }


    public ResponseEntity <List<QuestionWrapper>> getQuestionsById(Integer id) {
//        try{
//            Optional<Quiz> quiz=quizRepository.findById(id);
//            List<Questions> questionFromDB=quiz.get().getQuestions();
            List<QuestionWrapper> questionForUser=new ArrayList<>();
//
//            for(Questions q:questionFromDB ){
//                QuestionWrapper qw=new QuestionWrapper(q.getId(),q.getProblem(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
//                questionForUser.add(qw);
//            }
//            return new ResponseEntity<>(questionForUser,OK);
//        }catch(Exception e){
//            e.printStackTrace();
//        }
        return new ResponseEntity<>(new ArrayList<>(), BAD_REQUEST);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {

//        Quiz quiz=quizRepository.findById(id).get();
//        List<Integer> questions=
        int right=0;
//        int i=0;
//        for(Response response: responses) {
//            if (response.getResponse().equals(questions.get(i).getRight_option()))
//                right++;
//            i++;
//        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
