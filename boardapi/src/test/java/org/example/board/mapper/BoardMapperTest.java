package org.example.board.mapper;

import lombok.extern.log4j.Log4j;
import org.example.board.domain.BoardVO;
import org.example.config.RootConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
//@Transactional : 테슽트가 성공하더라도 자동으로 롤백된다
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RootConfig.class})
@Log4j
public class BoardMapperTest {
    @Autowired
    private BoardMapper mapper;

    @Test
    @DisplayName("BoardMapper 의 목록 불러오기")
    public void getList() {
//        모든 BoardVO 리스트를 가져와서 각 객체를 출력한다
        for(BoardVO board : mapper.getList()) {
            log.info(board);
        }
    }
    @Test
    @DisplayName("BoardMapper의 게시글 읽기")
    public void get() {
//        존재하는 게시물 번호로 테스트
        BoardVO board = mapper.get(1L);

        log.info(board);
    }
    @Test
    @DisplayName("BoardMapper의 새글 작성")
    public void create(){
        BoardVO board = new BoardVO();
        board.setTitle("새로 작성하는 글");
        board.setContent("새로 작성하는 내용");
        board.setWriter("scoula");

        mapper.create(board);

        log.info(board);
    }
    @Test
    @DisplayName("BoardMapper의 글 수정")
    public void update(){

        BoardVO board = new BoardVO();
//        no에는 존재하는 no값을 적어줘야 제대로 수정이 된다
        board.setNo(4L);
        board.setTitle("수정된 제목");
        board.setContent("수정된 내용");
        board.setWriter("user00");

        int count = mapper.update(board);

        log.info("UPDATE COUNT: " + count);
    }

    @Test
    @DisplayName("BoardMapper의 글 삭제")
    public void delete(){
// 삭제된 행의 개수 출력
        log.info("DELETE COUNT: " + mapper.delete(4L));
    }
}