package org.example.ex03.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class SampleDTOList {
    private List<SampleDTO> list;

//    생성자는 클래스 이름과 같음
    public SampleDTOList() {
        list = new ArrayList<>();
    }
}
