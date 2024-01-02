package com.td.TrenD.controller;

import com.td.TrenD.model.TotalSearchVO;
import com.td.TrenD.model.TrendVO;
import com.td.TrenD.service.TrendService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/main")  // 기본 경로 설정
@RequiredArgsConstructor
public class MainController {

    private final TrendService trendService;

    @GetMapping("/search/comm")
    @ResponseBody
    public TotalSearchVO commTotalSearch(@RequestParam String keyword, @RequestParam(defaultValue = "1") String page) {

        String cateCd = "t";
        int count = trendService.commSearchResultCount("t", keyword);
        int size = 10;
        int lastPage = 0;
        if (count > 1) {
            lastPage = (count - 1) / size + 1;
        }
        int currentPage = Integer.parseInt(page) - 1;
        if (currentPage < 0) {currentPage = 0;}
        if (currentPage + 1 > lastPage) { currentPage = lastPage - 1;}
        int pageRange = 10;
        int startPage = currentPage - (currentPage % pageRange) + 1;
        int endPage = currentPage - currentPage % pageRange + pageRange;
        if (endPage > lastPage){endPage = lastPage;}

        Pageable pageable = PageRequest.of(currentPage, size);
        List<TrendVO> searchResult = trendService.commSearchResult(keyword, cateCd, pageable).getContent();

        TotalSearchVO result = new TotalSearchVO();
        result.setSearchResult(searchResult);
        result.setCurrentPage(Integer.toString(currentPage));
        result.setLastPage(Integer.toString(lastPage));
        result.setStartPage(Integer.toString(startPage));
        result.setEndPage(Integer.toString(endPage));
        result.setPageRange(Integer.toString(pageRange));

        return result;
    }

    @GetMapping("/search/trend")
    @ResponseBody
    public TotalSearchVO trendTotalSearch(@RequestParam String keyword, @RequestParam(defaultValue = "1") String page) {

        String cateCd = "t";
        int count = trendService.trendSearchResultCount("t", keyword);
        int size = 10;
        int lastPage = 0;
        if (count > 1) {
            lastPage = (count - 1) / size + 1;
        }
        int currentPage = Integer.parseInt(page) - 1;
        if (currentPage < 0) {currentPage = 0;}
        if (currentPage + 1 > lastPage) { currentPage = lastPage - 1;}
        int pageRange = 10;
        int startPage = currentPage - (currentPage % pageRange) + 1;
        int endPage = currentPage - currentPage % pageRange + pageRange;
        if (endPage > lastPage){endPage = lastPage;}

        Pageable pageable = PageRequest.of(currentPage, size);
        List<TrendVO> searchResult = trendService.trendSearchResult(keyword, cateCd, pageable).getContent();

        TotalSearchVO result = new TotalSearchVO();
        result.setSearchResult(searchResult);
        result.setCurrentPage(Integer.toString(currentPage));
        result.setLastPage(Integer.toString(lastPage));
        result.setStartPage(Integer.toString(startPage));
        result.setEndPage(Integer.toString(endPage));
        result.setPageRange(Integer.toString(pageRange));

        return result;
    }

}