package org.example.common.util;

public class UploadFileName {
    public static String getUniqueFileName(String fileName) {

//      lastIndexOf(문자열) : 뒤에서부터 해당 문자열을 찾아서 처음 등장하는 인덱스 반환
        int ix = fileName.lastIndexOf(".");
//        substring(시작인덱스, 끝인덱스+1): 시작인덱스부터 끝인덱스까지 문자열을 잘라냄
        String name = fileName.substring(0, ix);
//        substring에 시작인덱스만 있을 경우엔 시작인덱스부터 끝까지 잘라낸다
        String ext = fileName.substring(ix+1);

//파일명 뒤에 타임스탬프를 붙여서 고유한 파일명으로 만들어줌
        return String.format("%s-%d.%s", name, System.currentTimeMillis(), ext);
    }
}
