package org.example.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.example.board.domain.BoardAttachmentVO;
import org.example.board.domain.BoardVO;
import org.example.board.dto.BoardDTO;
import org.example.board.mapper.BoardMapper;
import org.example.common.util.UploadFiles;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Log4j
@Service // Service 역할을 하는 Bean 등록
@RequiredArgsConstructor // final필드로 생성자 추가
public class BoardServiceImpl implements BoardService {
//    업로드시 해당 경로가 없으면 생성하도록 처리해뒀으므로 폴더가 없어도 상관없다
    private final static String BASE_DIR = "c:/Upload/board";
//    생ㅇ성자가 하나 있다면 그 생성자로 주입 가능
    final private BoardMapper mapper;

    @Override
    public List<BoardDTO> getList() {

        log.info("getList...........");
        return mapper.getList().stream() // BoardVO의 스트림
                .map(BoardDTO::of) //전부 BoardDTO로 변환 -> BoardDTO의 스트림
                .toList();// BoardDTO의 리스트로 변환
    }

    @Override
    public BoardDTO get(Long no) {

        log.info("get............." + no);

        BoardDTO board = BoardDTO.of(mapper.get(no));
//        board 객체가 null이면 NoSuchElementException 예외 발생, null이 아니면 해당 객체 반환
        return Optional.ofNullable(board)
                .orElseThrow(NoSuchElementException::new);
    }
//    2개 이상의 insert 문이 실행될 수 있으므로 트랜잭션 처리 필요
//    RuntimeException인 경우만 자동 rollback
    @Transactional
    @Override
    public void create(BoardDTO board) {

        log.info("create.........." + board);

        BoardVO boardVO = board.toVO();
        mapper.create(boardVO);

//        파일 업로드 처리
        List<MultipartFile> files = board.getFiles();
        if(files != null && !files.isEmpty()) { //첨부 파일이 있는 경우
            upload(boardVO.getNo(),files);
        }
    }
//    해당 게시물에 참조 파일들을 추가해주는 메소드
    private void upload(Long bno, List<MultipartFile> files) {
        for (MultipartFile part : files) {
//            첨부파일 목록에서 파일을 하났기 꺼내서 비어있는지 확인
//            비어있으면 다음 파일 확인
            if(part.isEmpty()) continue;
            try{
//                업로드 경로 생성 후 BoardAttachmentVO 객체 생성
                String uploadPath = UploadFiles.upload(BASE_DIR, part);
                BoardAttachmentVO attach = BoardAttachmentVO.of(part, bno, uploadPath);
//                BoardAttachmentVO 테이블에 참조파일 데이터 하나추가
                mapper.createAttachment(attach);
            }catch (IOException e){
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean update(BoardDTO board) {

        log.info("update.........." + board);
//        mapper의 update를 호출해서 수정된 행의 수가 1일 경우 true 반환
        return mapper.update(board.toVO()) == 1;
    }

    @Override
    public boolean delete(Long no) {

        log.info("delete..........." + no);

//        삭제된 행의 수가 1인지 확인해서 boolean 반환
        return mapper.delete(no) == 1;
    }

//첨부파일 한개 얻기
    @Override
    public BoardAttachmentVO getAttachment(Long no) {
        return mapper.getAttachment(no);
    }
//첨부파일 삭제
    @Override
    public boolean deleteAttachment(Long no) {
        return mapper.deleteAttachment(no) == 1;
    }
}
