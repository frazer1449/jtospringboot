package com.example.test2.answer;
// answer의 package가 subpackage로 바뀜.

import java.time.LocalDateTime;

import com.example.test2.question.Question;
import com.example.test2.user.SiteUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @ManyToOne
    private Question question;
//    Question: entity; 관계 정의
//    ManyToOne (answer : question = many : one)

    @ManyToOne
    private SiteUser author;

}
