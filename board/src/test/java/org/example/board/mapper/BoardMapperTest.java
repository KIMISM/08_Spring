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

import static org.junit.jupiter.api.Assertions.*;
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
        BoardVO board = mapper.get(1L);

        log.info(board);
    }
}