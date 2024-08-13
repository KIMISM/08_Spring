package org.example.board.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.example.common.util.UploadFiles;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Log4j
public class BoardAttachmentVO {
    private Long no;
    private Long bno;
    private String filename;
    private String path;
    private String contentType;
    private Long size;
    private Date regDate;

//    펙토리 메소드
//    part,bno,path 세가지 값을 받아서 BoardAttachmentVO 객체 생성
    public static BoardAttachmentVO of(MultipartFile part,Long bno, String path) {
    return builder()
            .bno(bno)
            .filename(part.getOriginalFilename())
            .path(path)
            .contentType(part.getContentType())
            .size(part.getSize())
            .build();
    }
//    size를 보기 쉬운 포맷 형식으로 변환해준다
    public String getFileSize() {
        return UploadFiles.getFormatSize(size);
    }
}
