package com.td.TrenD.controller;

import com.td.TrenD.model.TrendReVO;
import com.td.TrenD.model.TrendVO;
import com.td.TrenD.service.MypageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

// ...

@Controller
@RequestMapping("/mypage")
public class MypageController {

    @Autowired
    private MypageService myservice;

    @GetMapping("/userpage")
    public String mypage(HttpSession session) {
        // 세션에서 userId를 가져옵니다.
        String userId = (String) session.getAttribute("userId");

        // userId가 null이면 로그인되지 않은 상태이므로 로그인 페이지로 이동.
        if (userId == null) {
            return "redirect:/loginform";
        }

        // userId가 있다면 로그인된 상태이므로 마이페이지로 이동.
        return "mypage/userpage";
    }

    @GetMapping("/boardlist/{page}")
    public ResponseEntity<Map<String, Object>> getBoardList(@PathVariable("page") int page, HttpSession session) {
        int limit = 10;
        PageRequest pageable = PageRequest.of(page - 1, limit);

        String userId = (String) session.getAttribute("userId");

        if (userId == null) {
            // 로그인되지 않은 사용자의 처리
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Page<TrendVO> boardPage = myservice.getBoardList(pageable, userId);

        int pageCount = boardPage.getTotalPages();
        int startPage = ((page - 1) / 10) * limit + 1;
        int endPage = startPage + 10 - 1;

        if (endPage > pageCount)
            endPage = pageCount;

        Map<String, Object> response = new HashMap<>();
        response.put("isAdmin", myservice.isAdmin(userId));
        response.put("page", page);
        response.put("listcount", boardPage.getTotalElements());
        response.put("boardlist", boardPage.getContent());
        response.put("pageCount", pageCount);
        response.put("startPage", startPage);
        response.put("endPage", endPage);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/replylist/{page}")
    public ResponseEntity<Map<String, Object>> getReplyList(@PathVariable("page") int page, HttpSession session) {
        int limit = 10;
        PageRequest pageable = PageRequest.of(page - 1, limit);

        String userId = (String) session.getAttribute("userId");

        if (userId == null) {
            // 로그인되지 않은 사용자의 처리
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Page<TrendReVO> replyPage = myservice.getReplyList(pageable, userId);

        int pageCount = replyPage.getTotalPages();
        int startPage = ((page - 1) / 10) * limit + 1;
        int endPage = startPage + 10 - 1;

        if (endPage > pageCount)
            endPage = pageCount;

        Map<String, Object> response = new HashMap<>();
        response.put("isAdmin", myservice.isAdmin(userId));
        response.put("page", page);
        response.put("listcount", replyPage.getTotalElements());
        response.put("replylist", replyPage.getContent());
        response.put("pageCount", pageCount);
        response.put("startPage", startPage);
        response.put("endPage", endPage);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @GetMapping("/replycontent/{trNo}")
//    public String getReplyContent(@PathVariable int trNo, Model model) {
//        TrendReVO replyInfo = myservice.getReplyByTrNo(trNo);
//        model.addAttribute("replyInfo", replyInfo);
//
//        // 확인된 cateCd 값에 따라 리다이렉션
//        if ("t".equals(replyInfo.getTrendVO().getCateCd())) {
//            return "redirect:/trendPost?trNo=" + trNo;
//        } else {
//            return "redirect:/commPost?trNo=" + trNo;
//        }
//    }

}
