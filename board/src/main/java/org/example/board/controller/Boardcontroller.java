package org.example.board.controller;

import java.io.File;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.example.board.domain.BoardAttachmentVO;
import org.example.board.dto.BoardDTO;
import org.example.board.service.BoardService;
import org.example.common.util.UploadFiles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller // controller 기능을 하는 빈 등록
@Log4j
@RequestMapping("/board") //url 공통주소 할당
@RequiredArgsConstructor //생성자 주입
public class Boardcontroller {

    final private BoardService service;

//    http://localhost:8080/board/list
//    return 값이 void기 때문에 뷰도 경로가 동일 board/list
    @GetMapping("/list")
    public void list(Model model) {

        log.info("list");
//        Model의 속성에 데이터를 담을 경우 뷰로 전달 가능
        model.addAttribute("list", service.getList());
    }
    @GetMapping("/create")
    public void create(){
        log.info("create");
    }
    @PostMapping("/create")
    public String cteate(BoardDTO board) {
        log.info("create: "+ board);
//        service-> mapper.java -> mapper.xml
        service.create(board);
// 게시물 목록 페이지로 리다이렉트
        return "redirect:/board/list";
    }
    @GetMapping({"/get","/update"}) // "/get"과 "/update" 경로를 둘다 처리
    //@RequestParam : 주소 뒤에 ?를 붙여서 쿼리스트링으로 정보를 받아준다
    public void get(@RequestParam("no") Long no, Model model) {

        log.info("/get or update");
//        Model 객체는 데이터를 뷰로 전달하기 위해 사용
        model.addAttribute("board", service.get(no));
    }
    @PostMapping("/update")
    public String update(BoardDTO board) {

        log.info("update: " + board);

        service.update(board);
//수정한 후 목록 페이지로 리다이렉트
        return "redirect:/board/list";
    }
    @PostMapping("/delete")
    public String delete(@RequestParam("no") Long no) {

        log.info("delete... " + no);
        service.delete(no);
//수정한 후 목록 페이지로 리다이렉트
        return "redirect:/board/list";
    }
    @GetMapping("/download/{no}")
    @ResponseBody //view를 사용하지 않고 json으로 받아올때 주로 사용
    public void download(@PathVariable("no") Long no, HttpServletResponse response) throws Exception {
//받아온 번호에 해당하는 첨부 파일 가져오기
        BoardAttachmentVO attach = service.getAttachment(no);

        File file = new File(attach.getPath());
//해당 부분에서 실제 파일 다운로드 작업
        UploadFiles.download(response, file, attach.getFilename());
    }
}
