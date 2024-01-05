package com.td.TrenD.controller;

import com.td.TrenD.model.CategoryVO;
import com.td.TrenD.model.StatisticsVO;
import com.td.TrenD.model.TrendVO;
import com.td.TrenD.model.UserVO;
import com.td.TrenD.service.StatisticsService;
import com.td.TrenD.service.TrendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class TrendController {

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

}
