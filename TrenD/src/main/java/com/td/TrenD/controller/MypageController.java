package com.td.TrenD.controller;

import com.td.TrenD.model.TrendReVO;
import com.td.TrenD.model.TrendVO;
import com.td.TrenD.service.MypageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/mypage")
public class MypageController {

    @Autowired
    private MypageService myservice;

    @GetMapping("/mypage")
    public String mypage() {
        return "mypage/mypage";
    }

    // 글목록
    @GetMapping("/boardlist/{page}")
    @ResponseBody
    public Page<TrendVO> boardlist(@PathVariable("page") int page, Model model) {
        int limit = 10;
        PageRequest pageable = PageRequest.of(page - 1, limit);

        Page<TrendVO> boardPage = myservice.getBoardList(pageable);

        // 총 페이지
        int pageCount = boardPage.getTotalPages();

        int startPage = ((page - 1) / 10) * limit + 1;
        int endPage = startPage + 10 - 1;

        if (endPage > pageCount)
            endPage = pageCount;

        model.addAttribute("page", page);
        model.addAttribute("listcount", boardPage.getTotalElements());
        model.addAttribute("boardlist", boardPage.getContent());
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return boardPage;
    }

    // 댓글목록
    @GetMapping("/replylist/{page}")
    @ResponseBody
    public Page<TrendReVO> replylist(@PathVariable("page") int page, Model model) {
        int limit = 10;
        PageRequest pageable = PageRequest.of(page - 1, limit);

        Page<TrendReVO> replyPage = myservice.getReplyList(pageable);

        int pageCount = replyPage.getTotalPages();

        int startPage = ((page - 1) / 10) * limit + 1;
        int endPage = startPage + 10 - 1;

        if (endPage > pageCount)
            endPage = pageCount;

        model.addAttribute("page", page);
        model.addAttribute("listcount", replyPage.getTotalElements());
        model.addAttribute("replylist", replyPage.getContent());
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return replyPage;
    }
}
