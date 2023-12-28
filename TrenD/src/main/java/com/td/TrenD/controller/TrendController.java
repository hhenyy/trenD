package com.td.TrenD.controller;

import com.td.TrenD.model.TrendVO;
import com.td.TrenD.service.TrendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/trend")
public class TrendController {

    @Autowired
    private TrendService trendService;

    @GetMapping("/trendList")
    public String trend() {
        return "trend/trendList";
    }

    @GetMapping("/list/{page}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> trendList(@PathVariable("page") int page) {
        System.out.println("TrendList");
        int limit = 10;
        PageRequest pageable = PageRequest.of(page - 1, limit);

        try {
            Page<TrendVO> trendPage = trendService.getTrendList(pageable);
            System.out.println(trendPage);

            Map<String, Object> response = new HashMap<>();
            response.put("page", page);
            response.put("listCount", trendPage.getTotalElements());
            response.put("trendList", trendPage.getContent());
            response.put("pageCount", trendPage.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            // 예외 처리
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
