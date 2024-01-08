package com.td.TrenD.controller;

import com.td.TrenD.model.CategoryVO;
import com.td.TrenD.model.StatisticsVO;
import com.td.TrenD.model.TrendVO;
import com.td.TrenD.model.UserVO;
import com.td.TrenD.service.CommunityService;
import com.td.TrenD.service.StatisticsService;
import com.td.TrenD.service.TrendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class PostController {

    @Autowired
    private CommunityService commService;

    @Autowired
    private TrendService trendService;

    @Autowired
    private StatisticsService staticsService;


    @RequestMapping("post")
    public String commContent(HttpServletRequest request, HttpSession session, Model model) {


        TrendVO post = new TrendVO();
        int trNo = Integer.parseInt(request.getParameter("trNo"));

        post = trendService.trendContent(trNo);
        if (post.getTrDelYn() == 'n') {
            int readCount = post.getTrReadCount() + 1;
            post.setTrReadCount(readCount);
            trendService.saveTrend(post);


            UserVO user = new UserVO();
            String userId = (String) session.getAttribute("userId");
            if (userId != null) {
                StatisticsVO statics = new StatisticsVO();
                statics = staticsService.checkStatics(userId, trNo);
                if (statics == null) {
                    statics = new StatisticsVO();
                    statics.setTrNo(trNo);
                    user.setUserId(userId);
                    statics.setUserVO(user);
                    staticsService.saveStatics(statics);
                }
            }
        }

        model.addAttribute("post", post);

        return "trend/trendContent";
    }

    @RequestMapping("totalSearch")
    public String totalSearch(HttpServletRequest request, Model model) {

        model.addAttribute("keyword", request.getParameter("keyword"));

        return "main/totalSearch";
    }

    @RequestMapping("viewTrend")
    public String viewTrend(@RequestParam("trend") String trend) {

        TrendVO result = trendService.findTrend(trend);

        String trNo = Integer.toString(result.getTrNo());

        return "redirect:post?trNo=" + trNo;
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


}
