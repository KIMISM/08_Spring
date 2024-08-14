package org.example.board.service;

import lombok.extern.log4j.Log4j;
import org.example.board.dto.BoardDTO;
import org.example.config.RootConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RootConfig.class})
@Log4j
class BoardServiceTest {

    @Autowired
    private BoardService service;

    @Test
    void getList() {

//        BoardService를 구현한 BoardServiceImpl 의 getList호출
//        List<BoardDTO>를 반환한 후 해당 리스트를 순회
        for(BoardDTO board: service.getList()) {
            log.info(board);
        }
    }

    @Test
    void get() {
        log.info(service.get(1L));
    }

    @Test
    void create() {
        BoardDTO board = new BoardDTO();
        board.setTitle("새로 작성하는 글");
        board.setContent("새로 작성하는 내용");
        board.setWriter("scoula");

        service.create(board);

        log.info("생성된 게시물의 번호: "+ board.getNo());
    }

    @Test
    void update() {
        BoardDTO board = service.get(8L); //수정할 계시글 가져오기

        board.setTitle("제목 수정합니다.");
        log.info("update RESULT: " + service.update(board));
    }

    @Test
    void delete() {

        log.info("delete RESULT: " + service.delete(5L));
    }
}