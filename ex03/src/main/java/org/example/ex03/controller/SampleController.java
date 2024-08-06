package org.example.ex03.controller;


import lombok.extern.log4j.Log4j;
import org.example.ex03.dto.SampleDTO;
import org.example.ex03.dto.SampleDTOList;
import org.example.ex03.dto.TodoDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Arrays;

@Controller //bean 등록 + Controller 명시
@RequestMapping("/sample") //해당 클래스에 있는 메소드는 해당 url로 매핑하겠다
@Log4j
public class SampleController {

    @RequestMapping("")
//    클래스 안에 있는 건 메소드
//    localhost:8080/sample
    public void basic(){
        log.info("basic............");
    }
//    GET과 POST 두개 다 모두 처리하는 메소드
//    locallhost:8080/sample/basic
//    method가 객체여서 중괄호({)로 묶어줘야함
    @RequestMapping(value="/basic",method = {RequestMethod.GET,RequestMethod.POST})
    public void basicGet(){
        log.info("basic get...........");
    }
//     @RequestMapping + Get 요청을 합친 어노테이션, GET요청만 처리 가능
//    locallhost:8080/sample/basicOnlyGet
    @GetMapping("/basicOnlyGet")
    public void basicGet2(){
        log.info("basic get only get...........");
    }
    //    locallhost:8080/sample/ex01
    @GetMapping("/ex01")
    public String ex01(SampleDTO dto){
//        넣어준 값이 없을 경우 초기값으로 나온다.
        log.info("" + dto);

        return "ex01"; //뷰의 이름 반환
    }
//     @RequestParam은 DTO 객체를 안쓰고 각각 파라미터를 받아줄때 사용
//    필드 두개 다 제대로 안넣어주면 예외 발생, 개별 파라미터는 뷰로 전달 불가능
    @GetMapping("/ex02")
    public String ex02(
            @RequestParam("name") String name,
            @RequestParam("age") int age){
       log.info("name:" + name);
       log.info("age:" + age);

       return "ex02";
    }
//    같은 이름으로 여러번 전달하는 경우 List나 배열로 받아올 수 있다.
//    locallhost:8080/sample/ex02List
//    @GetMapping("/ex02List")
//    public String ex02List(@RequestParam("ids") ArrayList<String> ids){
//        log.info("ids:" + ids);
//
//        return "ex02List";
//    }
    @GetMapping("/ex02List")
    public String ex02List(@RequestParam("ids") String[] ids){
        log.info("array ids:" + Arrays.toString(ids));

        return "ex02List";
    }
    @GetMapping("/ex02Bean")
    public String ex02Bean(SampleDTOList list){
        log.info("list dtos:" + list);

        return "ex02Bean";
    }
    @GetMapping("/ex03")
    public String ex03(TodoDTO todo){
        log.info("todo:" + todo);

        return "ex03";
    }
//    http://localhost:8080/sample/ex04?name=aaa&age=11&page=9
//    page를 기본 자료형으로 넘기면 뷰로 전달되지 않는다
//    따라서 @ModelAttribute로 전달해야 한다(request scope 저장)
    @GetMapping("/ex04")
    public String ex04(SampleDTO dto, @ModelAttribute("page")int page){
        log.info("dto:" + dto);
        log.info("page:" + page);

        return "sample/ex04";
    }
//    http://localhost:8080/sample/ex05
//    return 값이 void일 경우 요청 url을 기준으로 jsp 파일을 찾는다.
//    /WEB-INF/views/sample/ex05.jsp 파일을 찾게 됨
    @GetMapping("/ex05")
    public void ex05(){
        log.info("/ex05..........");
    }
    //    http://localhost:8080/sample/ex06
//    해당 경로 접근시 http://localhost:8080/sample/ex06-2?name=AAA&age=10로 리다이렉트함
    @GetMapping("/ex06")
    public String ex06(RedirectAttributes ra) {
        log.info("/ex06...........");
//        리다이렉트 시 요청 파라미터로 name과 age를 추가해준다
        ra.addAttribute("name","AAA");
        ra.addAttribute("age",10);
//      리다이렉트 시 "redirect:" 접두사를 사용한다
        return "redirect:/sample/ex06-2";
    }
    //    http://localhost:8080/sample/ex07
//    @ResponseBody 어노테이션은 반환된 객체가 JSON 형식으로 변환되어 보여지도록 한다
    @GetMapping("/ex07")
    public @ResponseBody SampleDTO ex07(){
        log.info("/ex07...........");

        SampleDTO dto = new SampleDTO();
        dto.setName("홍길동");
        dto.setAge(10);

        return dto;
    }
    //    http://localhost:8080/sample/ex08
//    ResponseEntity = json 형태의 body + 응답 헤더
    @GetMapping("/ex08")
    public ResponseEntity<String> ex08() {
        log.info("/ex08...........");

        String msg = "{\"name\":\"홍길동\"}"; //body에 들어갈 json 형태의 문자열

        HttpHeaders header = new HttpHeaders();
//        HttpHeaders 객체 생성 후 Content-Type 헤더 설정
        header.add("Content-Type", "application/json;charset=utf-8");

//        ResponseEntity 객체 내에 바디, 헤더, 상태코드(200)반환
        return new ResponseEntity<>(msg, header, HttpStatus.OK);
    }
    //    http://localhost:8080/sample/exUpload
    @GetMapping("/exUpload")
    public void exUpload(){
        log.info("/exUpload..........");
    }
    //    http://localhost:8080/sample/exUploadPost
//    MultipartFile 하나가 업로드한 파일 하나에 대응한다
    @PostMapping("/exUploadPost")
    public void exUploadPost(ArrayList<MultipartFile> files){

        for(MultipartFile file : files){
            log.info("----------------------------");
            log.info("name:" + file.getOriginalFilename()); //파일의 원래 이름 출력
            log.info("size:" + file.getSize()); //파일의 크기 출력
        }
    }
}
