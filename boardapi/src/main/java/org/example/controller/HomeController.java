package org.example.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//@Controller : 자바 빈으로 등록됨
@Controller
//@Slf4j : Lombok제공, log()라는 멤버 변수가 생긴다
@Slf4j
public class HomeController {
//    @GetMapping : GET 메소드로 "/"에 접근시 home() 실행
    @GetMapping("/")
    public String home() {
        log.info("===============> HomeController/");
        return "index";

//        return "redirect:/board/list";
    }
}
