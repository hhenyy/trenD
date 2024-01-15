package com.td.TrenD.controller;

import com.td.TrenD.model.CategoryVO;
import com.td.TrenD.model.TrendVO;
import com.td.TrenD.service.CommunityService;
import com.td.TrenD.service.StatisticsService;
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
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/community")
public class CommunityController {

    @Autowired
    private CommunityService commService;
  
    @Autowired
    private TrendService trendService;

    @Autowired
    private StatisticsService staticsService;


    @GetMapping("/posts")
    public String community() {
        return "community/commList";
    }

    @GetMapping("/list/{page}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getCommList(
            @PathVariable("page") int page,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "") String cateCd,
            @RequestParam(defaultValue = "") String search
            ) {

        System.out.println("CommunityList Controller - Page: " + page + ", Keyword: " + keyword + "cateCd:" +cateCd);

        int limit = 10;
        PageRequest pageable = PageRequest.of(page-1, limit);
        System.out.println("pageable : "+pageable);

        try {
            Page<TrendVO> communityPage;
            if (StringUtils.hasText(keyword)) {
                communityPage = commService.searchCommList(keyword, search, pageable);
                System.out.println("Keyword: " + keyword + " Search :" + search);
                System.out.println("SearchCommList");
            } else if (StringUtils.hasText(cateCd)){
                communityPage = commService.findCategoryList(cateCd, pageable);
                System.out.println("Category: " + cateCd);
                System.out.println("FindCategoryList");
            }else{
                communityPage = commService.findCommList(pageable);
                System.out.println("Keyword is invalid or empty.");
                System.out.println("CommunityList");
            }

            List<CategoryVO> findAllCategory = commService.findAllCategory();

            Map<String, Object> response = new HashMap<>();
            response.put("page", page);
            response.put("keyword", keyword);
            response.put("cateList", findAllCategory);
            response.put("listCount", communityPage.getTotalElements());
            response.put("commList", communityPage.getContent());
            response.put("pageCount", communityPage.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error in CommunityList Controller: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

