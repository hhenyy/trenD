package com.td.TrenD.controller;

import com.td.TrenD.model.StatisticsVO;
import com.td.TrenD.model.TrendVO;
import com.td.TrenD.model.UserVO;
import com.td.TrenD.service.StatisticsService;
import com.td.TrenD.service.TrendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TrendController {

    @Autowired
    private TrendService trendService;

    @Autowired
    private StatisticsService staticsService;

    @RequestMapping("post")
    public String commContent(HttpServletRequest request, Model model) {


        // 트렌드 글 처리 별도 조건문 처리

        UserVO user = new UserVO();

        String userId;
//        userId = request.getParameter("userId");
        userId = "sun";

        TrendVO post = new TrendVO();
        int trNo = Integer.parseInt(request.getParameter("trNo"));

        post = trendService.trendContent(trNo);
        if (post.getTrDelYn() == 'n') {
            int readCount = post.getTrReadCount() + 1;
            post.setTrReadCount(readCount);
            trendService.saveTrend(post);


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

        model.addAttribute("post", post);

        return "trend/trendContent";
    }

}
