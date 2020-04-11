package com.example.springbootdemo.product.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PagetationDTO {
    private List<QuestionDto> questions;
    private Integer page;
    private Integer total;
    private Integer totalPage;
    private List<Integer> pages = new ArrayList<>();

    public void setPagetation(Integer total, int page, int size) {
        this.total = total;
        //获取总页数
        if(total%size==0){
            totalPage = total/size;
        }else{
            totalPage = total/size+1;
        }
        this.page = page;
        getPages(page);
    }

    private void getPages(int page) {
//        totalPage = 12;
        //最多显示7个
        pages.add(page);
        for (int i=1;i<=3;i++){
            if(page-i>0){
                pages.add(0,page-i);
            }
            if(page+i<=totalPage){
                pages.add(page+i);
            }
        }
    }

    public static void main(String[] arg0){
        PagetationDTO pagetationDTO = new PagetationDTO();
        pagetationDTO.getPages(5);
        System.out.println(pagetationDTO.pages);
    }
}
