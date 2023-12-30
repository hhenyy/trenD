package com.td.TrenD.controller;

import com.td.TrenD.model.TrendReVO;
import com.td.TrenD.model.TrendVO;
import com.td.TrenD.service.MypageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/mypage")
public class MypageController {

    @Autowired
    private MypageService myservice;

    @GetMapping("/userpage")
    public String mypage() {
        return "mypage/userpage";
    }

    @GetMapping("/boardlist/{page}")
    public ResponseEntity<Map<String, Object>> getBoardList(@PathVariable("page") int page) {
        int limit = 10;
        PageRequest pageable = PageRequest.of(page - 1, limit);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        Page<TrendVO> boardPage = myservice.getBoardList(pageable, userId);

        int pageCount = boardPage.getTotalPages();
        int startPage = ((page - 1) / 10) * limit + 1;
        int endPage = startPage + 10 - 1;

        if (endPage > pageCount)
            endPage = pageCount;

        Map<String, Object> response = new HashMap<>();
        response.put("page", page);
        response.put("listcount", boardPage.getTotalElements());
        response.put("boardlist", boardPage.getContent());
        response.put("pageCount", pageCount);
        response.put("startPage", startPage);
        response.put("endPage", endPage);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/replylist/{page}")
    public ResponseEntity<Map<String, Object>> getReplyList(@PathVariable("page") int page) {
        int limit = 10;
        PageRequest pageable = PageRequest.of(page - 1, limit);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        Page<TrendReVO> replyPage = myservice.getReplyList(pageable, userId);

        int pageCount = replyPage.getTotalPages();
        int startPage = ((page - 1) / 10) * limit + 1;
        int endPage = startPage + 10 - 1;

        if (endPage > pageCount)
            endPage = pageCount;

        Map<String, Object> response = new HashMap<>();
        response.put("page", page);
        response.put("listcount", replyPage.getTotalElements());
        response.put("replylist", replyPage.getContent());
        response.put("pageCount", pageCount);
        response.put("startPage", startPage);
        response.put("endPage", endPage);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
