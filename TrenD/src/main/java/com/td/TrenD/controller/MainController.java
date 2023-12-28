package com.td.TrenD.controller;

import com.td.TrenD.model.TrendVO;
import com.td.TrenD.service.TrendService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/main")  // 기본 경로 설정
@RequiredArgsConstructor
public class MainController {

    private final TrendService trendService;

    @GetMapping("/search")
    @ResponseBody
    public List<TrendVO> totalSearch(@RequestParam String keyword) {
        return trendService.trendSearch(keyword);
    }

}