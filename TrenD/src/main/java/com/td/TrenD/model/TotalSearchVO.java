package com.td.TrenD.model;

import lombok.Data;

import java.util.List;

@Data
public class TotalSearchVO {

    private List<TrendVO> searchResult;
    private String currentPage;
    private String startPage;
    private String endPage;
    private String lastPage;
    private String pageRange;
    private String count;


}
