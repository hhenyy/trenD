package com.td.TrenD.controller;

import com.td.TrenD.model.CategoryVO;
import com.td.TrenD.model.TrendVO;
import com.td.TrenD.model.UserVO;
import com.td.TrenD.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.td.TrenD.service.StatisticsService;
import com.td.TrenD.service.TrendService;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("/api/community")
public class CommunityController {

    @Autowired
    private CommunityService commService;
  
    @Autowired
    private TrendService trendService;

    @Autowired
    private StatisticsService staticsService;

    @GetMapping("/commList")
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
  
    @RequestMapping("commForm")
    public String commForm(Model model) {

        List<CategoryVO> categoryList = commService.findAllCategory();
        model.addAttribute("categoryList", categoryList);

        return "community/commForm";
    }

    @RequestMapping("commInsert")
    public String commInsert(HttpServletRequest request, HttpSession session) {

        String userId = (String) session.getAttribute("userId");

        TrendVO comm = new TrendVO();
        UserVO user = new UserVO();
        user.setUserId(userId);

        comm.setUserVO(user);
        comm.setCateCd(request.getParameter("cateCd"));
        comm.setTrSubject(request.getParameter("trSubject"));
        comm.setTrContent(request.getParameter("trContent"));

        comm.setTrDate(new Date());
        comm.setTrUpdate(new Date());
        comm.setTrDelYn('n');
        comm.setTrReadCount(0);

        TrendVO result = new TrendVO();
        result = trendService.saveTrend(comm);
        System.out.println(result);


        return "redirect:/";
    }


    @RequestMapping("commUpdateForm")
    public String commUpdateForm(HttpServletRequest request, Model model) {

        List<CategoryVO> categoryList = commService.findAllCategory();
        model.addAttribute("categoryList", categoryList);


        TrendVO post = new TrendVO();
        int trNo = Integer.parseInt(request.getParameter("trNo"));
        post = trendService.trendContent(trNo);
        model.addAttribute("post", post);

        return "community/commUpdate";
    }

    @RequestMapping("commUpdate")
    public String commUpdate(HttpServletRequest request, Model model) {


        int trNo = Integer.parseInt(request.getParameter("trNo"));
        String cateCd = request.getParameter("cateCd");
        String trSubject = request.getParameter("trSubject");
        String trContent = request.getParameter("trContent");

        TrendVO trendVO = commService.findById(trNo);

        trendVO.setTrNo(trNo);
        trendVO.setCateCd(cateCd);
        trendVO.setTrSubject(trSubject);
        trendVO.setTrContent(trContent);
        trendVO.setTrUpdate(new Date());

        trendService.saveTrend(trendVO);

        return "redirect:post?trNo=" + trNo;
    }

    @RequestMapping("deletePost")
    public String deletePost(HttpServletRequest request, Model model) {

        int trNo = Integer.parseInt(request.getParameter("trNo"));

        TrendVO trendVO = commService.findById(trNo);
        trendVO.setTrUpdate(new Date());
        trendVO.setTrDelYn('y');

        trendService.saveTrend(trendVO);

        return "redirect:/";
    }

    @RequestMapping("totalSearch")
    public String searchTest(HttpServletRequest request, Model model) {

        model.addAttribute("keyword", request.getParameter("keyword"));

        return "main/totalSearch";
    }

}
