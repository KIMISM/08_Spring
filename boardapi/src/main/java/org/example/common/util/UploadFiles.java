package org.example.common.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;

public class UploadFiles {
    public static String upload(String baseDir, MultipartFile part) throws IOException {
        
        File base = new File(baseDir);
//       exists 메소드로 해당 경로가 존재하는지 체크
        if (!base.exists()) {
            base.mkdirs(); // mkdirs: 중간에 존재하지 않는 디렉토리까지 모두 생성
        }
        String filename = part.getOriginalFilename(); // 원본파일명 저장
//        base 디렉토리 내에 고유한 이름을 가지는 파일 생성
        File dest = new File(baseDir, UploadFileName.getUniqueFileName(filename));
        
        part.transferTo(dest); //지정한 경로로 업로드 파일 이동
        return dest.getPath(); //지정한 파일 경로 리턴
    }
//    파일 크기를 사람이 읽기 쉬운 형식으로 변환
//    1,,225,957바이트 -> "1.2MB"
    public static String getFormatSize(Long size) {
        if(size <= 0)
            return "0";
        final String[] units = new String[]{"Bytes", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024,digitGroups)) + " " + units[digitGroups];
    }
    public static void download(HttpServletResponse response, File file, String orgName) throws Exception {

        response.setContentType("application/download");
        response.setContentLength((int)file.length());

        String filename = URLEncoder.encode(orgName, "UTF-8");
        response.setHeader("Content-disposition", "attachment; filename=\"" + filename + "\"");

        try(OutputStream os = response.getOutputStream()) {
            BufferedOutputStream bos = new BufferedOutputStream(os); {
            Files.copy(Paths.get(file.getPath()),bos);
            }
        }
    }
}
