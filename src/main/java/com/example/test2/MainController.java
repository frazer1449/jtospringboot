package com.example.test2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
//    명령어 가져오기
    @GetMapping("/hello")
    @ResponseBody
    public String hello(){
        return "Hello World";
    }
    @GetMapping("/sum")
    @ResponseBody
    public String sum(){

        int sum = 0;
        for (int i = 1; i <= 20; i++){
            sum += i;
        }
        String s = "" + sum;
        return s;
    }

    @GetMapping("/*")
    public String root() {
        return "redirect:/question/list";
    }
}
