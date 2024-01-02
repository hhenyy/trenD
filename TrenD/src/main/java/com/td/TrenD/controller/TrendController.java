package com.td.TrenD.controller;

import com.td.TrenD.model.TrendVO;
import com.td.TrenD.service.TrendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
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

//    @GetMapping("/list/{page}")
//    @ResponseBody
//    public ResponseEntity<Map<String, Object>> trendList(@PathVariable("page") int page) {
//        System.out.println("TrendList");
//        int limit = 10;
//        PageRequest pageable = PageRequest.of(page - 1, limit);
//
//        try {
//            Page<TrendVO> trendPage = trendService.getTrendList(pageable);
//            System.out.println(trendPage);
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("page", page);
//            response.put("listCount", trendPage.getTotalElements());
//            response.put("trendList", trendPage.getContent());
//            response.put("pageCount", trendPage.getTotalPages());
//
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } catch (Exception e) {
//            // 예외 처리
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @GetMapping("/search/{page}")
//    @ResponseBody
//    public ResponseEntity<Map<String, Object>> searchTrendList(@RequestParam String keyword, @PathVariable("page") int page
//                                                                , TrendPagingVO trendPagingVO) {
//        System.out.println("TrendSearchList");
//        System.out.println("Page : " +page);
//        System.out.println("Keyword : " +keyword);
//
//        int limit = 10;
//        PageRequest pageable = PageRequest.of(page - 1, limit, trendPagingVO.get);
//
//        try {
//            Page<TrendVO> trendPage = trendService.searchTrendList(keyword, pageable);
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("page", page);
//            response.put("listCount", trendPage.getTotalElements());
//            response.put("trendList", trendPage.getContent());
//            response.put("pageCount", trendPage.getTotalPages());
//
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @GetMapping("/list/{page}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getTrendList(
            @PathVariable("page") int page,
            @RequestParam(defaultValue = "") String keyword) {

        System.out.println("TrendList Controller - Page: " + page + ", Keyword: " + keyword);

        int limit = 10;
        PageRequest pageable = PageRequest.of(page - 1, limit);

        try {
            Page<TrendVO> trendPage;
            if (StringUtils.hasText(keyword)) {
                trendPage = trendService.searchTrendList(keyword, pageable);
                System.out.println("TrendSearchList");
            } else {
                trendPage = trendService.getTrendList(pageable);
                System.out.println("TrendList");
            }

            Map<String, Object> response = new HashMap<>();
            response.put("page", page);
            response.put("keyword", keyword);
            response.put("listCount", trendPage.getTotalElements());
            response.put("trendList", trendPage.getContent());
            response.put("pageCount", trendPage.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error in TrendList Controller: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
