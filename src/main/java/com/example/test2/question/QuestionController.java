package com.example.test2.question;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class QuestionController {

//    private final QuestionRepository questionRepository;

    //    Controller Default 동작: @ResponseBody 없으면 template에 있는애를 return.
//    Response의 기본 타입이 HTML
//    ResponseBody 가 들어가면 String을 HTML파일에 감싸서 display
//    @GetMapping("/question/list")
//    public String questionList(){
//        return "question_list";
//    }

    private final QuestionService questionService;

    @GetMapping("/question/list")
    public String list(Model model) {
        List<Question> questionList = this.questionService.getList();
        model.addAttribute("questionList", questionList);
//        model: HTML파일에서 question들을 가져다 쓰는데 도와주는애
//        템플릿에서 question들을 가져다쓰게 연결
        return "question_list";
//      HTML 파일을 리턴할수있고 데이터만 전송도 가능...
//      display만 아니라 데이터 전송
    }

    @GetMapping(value = "/question/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

}
