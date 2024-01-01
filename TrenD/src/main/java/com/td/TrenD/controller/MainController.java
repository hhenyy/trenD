package com.td.TrenD.controller;

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
    public List<TrendVO> commTotalSearch(@RequestParam String keyword) {

        String cateCd = "t";

        int page = 0, size = 10;
        int count = trendService.commSearchResultCount("t", keyword);

        Pageable pageable = PageRequest.of(page, size);

        System.out.println(trendService.commSearchResult(keyword, cateCd, pageable).getContent());

        List<TrendVO> searchResult = trendService.commSearchResult(keyword, cateCd, pageable).getContent();

        // reponse DTO를 따로 만든다..?

        return searchResult;
    }

    @GetMapping("/search/trend")
    @ResponseBody
    public List<TrendVO> trendTotalSearch(@RequestParam String keyword) {

        String cateCd = "t";

        int page = 0, size = 10;
        int count = trendService.trendSearchResultCount("t", keyword);

        Pageable pageable = PageRequest.of(page, size);

        System.out.println(trendService.trendSearchResult(keyword, cateCd, pageable).getContent());

        List<TrendVO> searchResult = trendService.trendSearchResult(keyword, cateCd, pageable).getContent();


        return searchResult;
    }

}