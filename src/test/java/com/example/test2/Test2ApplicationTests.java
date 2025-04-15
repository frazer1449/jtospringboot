package com.example.test2;

import com.example.test2.answer.Answer;
import com.example.test2.answer.AnswerRepository;
import com.example.test2.question.Question;
import com.example.test2.question.QuestionRepository;
import com.example.test2.question.QuestionService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class Test2ApplicationTests {
//    Test 종류
// Unit Test -> 간단한 기능테스트
// Integration Test -> 기능의 조화
// UI Test -> UI Test

    @Autowired
    private QuestionRepository questionRepository;
//    questionRepository sends and places Question object to the DB.
//    respository를 통해서 CRUD를 한다

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionService questionService;

//  1. Save Question Data
    @Test
    void testJpa() {
        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q1);  // 첫번째 질문 저장

        Question q2 = new Question();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q2);  // 두번째 질문 저장
    }

//  2. View Question Data => a. findAll method
    @Test
    void testJpa2() {
//        JPA : findAll = SQL : SELECT *
        List<Question> all = this.questionRepository.findAll();
        assertEquals(2, all.size());

        Question q = all.get(0);
        assertEquals("sbb가 무엇인가요?", q.getSubject());
    }

//  2. View Question Data => b. findById method
    @Test
    void testJpa3() {
//        Optional? null이 될수있는 객체
//        Null때문에 생기는 error 핸들링 코드 (try-catch exception) 쓰기 귀찮음 => Optional이 좋은 이유
//        (null이 될수있는 값은 Optional로!)
        Optional<Question> oq = this.questionRepository.findById(1);
        if(oq.isPresent()) {
//  안에 값이 있을경우, .get()함수로 unwrapping한다.
            Question q = oq.get();
            assertEquals("sbb가 무엇인가요?", q.getSubject());
        }
    }

//    2. View Question Data => c. findBySubject method
    @Test
    void testJpa4() {
        Question q = this.questionRepository.findBySubject("sbb가 무엇인가요?");
        assertEquals(1, q.getId());
    }

//    2. View Question Data => d. findBySubjectAndContent method

    @Test
    void testJpa5() {
        Question q = this.questionRepository.findBySubjectAndContent(
                "sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
        assertEquals(1, q.getId());
    }

//    2. View Question Data => e. findBySubjectLike method

    @Test
    void testJpa6() {
        List<Question> qList = this.questionRepository.findBySubjectLike("sbb%");
        Question q = qList.get(0);
        assertEquals("sbb가 무엇인가요?", q.getSubject());
    }

//    3. Modify Question Data

    @Test
    void testJpa7() {
        Optional<Question> oq = this.questionRepository.findById(1);
        assertTrue(oq.isPresent());
        Question q = oq.get();
        q.setSubject("수정된 제목");
        this.questionRepository.save(q);
    }

//    4. Delete Question Data

    @Test
    void testJpa8() {
        assertEquals(2, this.questionRepository.count());
        Optional<Question> oq = this.questionRepository.findById(1);
        assertTrue(oq.isPresent());
        Question q = oq.get();
        this.questionRepository.delete(q);
        assertEquals(1, this.questionRepository.count());
    }

//    5. Save Answer Data

    @Test
    void testJpa9() {
        Optional<Question> oq = this.questionRepository.findById(2);
        assertTrue(oq.isPresent());
        Question q = oq.get();

        Answer a = new Answer();
        a.setContent("네 자동으로 생성됩니다.");
        a.setQuestion(q);  // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요하다.
        a.setCreateDate(LocalDateTime.now());
        this.answerRepository.save(a);
    }

//    6. View Answer Data

    @Test
    void testJpa10() {
        Optional<Answer> oa = this.answerRepository.findById(1);
        assertTrue(oa.isPresent());
        Answer a = oa.get();
        assertEquals(2, a.getQuestion().getId());
    }

//    7. Finding Question Data Through Answer Data vs. Finding Answer Data Through Question Data

    @Test
    void testJpa11(){
        Optional<Answer> op = this.answerRepository.findById(1);
        assertTrue(op.isPresent());
        Answer a = op.get();
        Question q = a.getQuestion();
        assertEquals("id는 자동으로 생성되나요?",q.getContent());
    }


//    Transactional keeps the DB session alive until the method ends.
    @Transactional
    @Test
    void testJpa12() {
        Optional<Question> oq = this.questionRepository.findById(2);
        assertTrue(oq.isPresent());
        Question q = oq.get();

//        Without @Transactional, this doesn't work.
//        After the QuestionRepository retrieves a Question object using the
//        findById method, the database session is closed.

//        Question, Answer class에서 @OneToMany, @ManyToOne annotation 할때
//        (fetch=FetchType.LAZY (기본값), fetch=FetchType.EAGER) 옵션
//        (retrieving the entire list q in advance when searching for an object is called the Eager method)
        List<Answer> answerList = q.getAnswerList();

        assertEquals(1, answerList.size());
        assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
    }

    // Create Test Data (for Paging)
    @Test
    void testJpa13() {
        for (int i = 1; i <= 300; i++) {
            String subject = String.format("테스트 데이터입니다:[%03d]", i);
            String content = "내용무";
            this.questionService.create(subject, content);
        }
    }
}
