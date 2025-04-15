package com.example.test2.question;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Question findBySubject(String subject);
//    findBy[Column Name]([Column Type] name)

//    비슷하게 AND말고 OR, IN, etc.
    Question findBySubjectAndContent(String subject, String content);

    List<Question> findBySubjectLike(String subject);

    Page<Question> findAll(Pageable pageable);
}
