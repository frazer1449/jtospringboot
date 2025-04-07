package com.example.test2.answer;

// 다른 페키지에 있는 Answer.
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {

}
