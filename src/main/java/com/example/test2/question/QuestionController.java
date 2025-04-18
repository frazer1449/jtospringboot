package com.example.test2.question;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.test2.answer.AnswerForm;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;

@RequestMapping("/question")
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

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value="page", defaultValue="0") int page) {
        Page<Question> paging = this.questionService.getList(page);
        model.addAttribute("paging", paging);
        return "question_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    @GetMapping("/create")
    public String questionCreate(Model model, QuestionForm questionForm){
//        model.addAttribute("questionForm", new QuestionForm());
        return "question_form";
    }

//    @PostMapping("/create")
//    public String questionCreate(@RequestParam(value="subject") String subject, @RequestParam(value="content") String content){
//        this.questionService.create(subject, content);
//        return "redirect:/question/list";
//    }

    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "question_form";
        }
        this.questionService.create(questionForm.getSubject(), questionForm.getContent());
        return "redirect:/question/list";
    }
}
