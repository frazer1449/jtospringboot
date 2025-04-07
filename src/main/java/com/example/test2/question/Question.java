package com.example.test2.question;

import java.time.LocalDateTime;
import java.util.List;

import com.example.test2.answer.Answer;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Question {
//    Primary Key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String subject;

//    String 길이 제한 없에기 위해 아래 annotation을 한다
    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

//    mapped by what? question에 여러개의 answer. question 지워지면 어떻게 처리?
//    question을 지우면 연결된 answer들 없어짐
//    possible to set question as null in Answer entity (basically not corresponding to a question)
//    REMOVE => ON DELETE CASCADE
//    (?) => ON DELETE SET NULL
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;
}
