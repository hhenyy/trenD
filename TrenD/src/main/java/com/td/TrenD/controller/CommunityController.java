package com.td.TrenD.controller;

import com.td.TrenD.model.CategoryVO;
import com.td.TrenD.model.StatisticsVO;
import com.td.TrenD.model.TrendVO;
//import com.td.TrenD.commService.CommunityService;
import com.td.TrenD.model.UserVO;
import com.td.TrenD.service.CommunityService;
import com.td.TrenD.service.StatisticsService;
import com.td.TrenD.service.TrendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
public class CommunityController {

    @Autowired
    private CommunityService commService;

    @Autowired
    private TrendService trendService;

    @Autowired
    private StatisticsService staticsService;


    @RequestMapping("/")
    public String test(Model model) {

        List<TrendVO> commList = new ArrayList<>();

        commList = commService.commList();

        model.addAttribute("commList", commList);

        return "community/commTest";
    }

    @RequestMapping("commForm")
    public String commForm(Model model) {

        List<CategoryVO> categoryList = commService.findAllCategory();
        model.addAttribute("categoryList", categoryList);

        return "community/commForm";
    }

    @RequestMapping("commInsert")
    public String commInsert(HttpServletRequest request) {

        System.out.println("commInsert");

        TrendVO comm = new TrendVO();


        comm.setUserId("sun");
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

    @RequestMapping("commContent")
    public String commContent(HttpServletRequest request, Model model) {


        // 트렌드 글 처리 별도 조건문 처리

        UserVO user = new UserVO();

        String userId;
//        userId = request.getParameter("userId");
        userId = "sun";

        TrendVO post = new TrendVO();
        int trNo = Integer.parseInt(request.getParameter("trNo"));

        post = commService.commContent(trNo);
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


        // =============== 통계 DB input

//      String dbin[] =  {"input11",
//                "input12",
//            "input21",
//                    "input22",
//            "input31",
//                    "input32",
//            "input41",
//                    "input42",
//            "input51",
//                    "input52"
//        };
//
//      for(int i = 0; i < 5; i++){
//
//          userId = dbin[(int)(Math.random()*10)];
//
//
//        TrendVO post = new TrendVO();
//        int trNo = Integer.parseInt(request.getParameter("trNo"));
//
//        post = commService.commContent(trNo);
//        if (post.getTrDelYn() == 'n') {
//            int readCount = post.getTrReadCount() + 1;
//            post.setTrReadCount(readCount);
//            trendService.saveTrend(post);
//        }
//
//        StatisticsVO statics = new StatisticsVO();
//        statics = staticsService.checkStatics(userId, trNo);
//        if(statics == null){
//            statics = new StatisticsVO();
//            statics.setTrNo(trNo);
//            user.setUserId(userId);
//            statics.setUserVO(user);
//            staticsService.saveStatics(statics);
//        }
//
//
//
//        model.addAttribute("post", post);
//
//      }
// =============== 통계 DB input

        return "community/commContent";
    }


    @RequestMapping("commUpdateForm")
    public String commUpdateForm(HttpServletRequest request, Model model) {

        List<CategoryVO> categoryList = commService.findAllCategory();
        model.addAttribute("categoryList", categoryList);


        TrendVO post = new TrendVO();
        int trNo = Integer.parseInt(request.getParameter("trNo"));
        post = commService.commContent(trNo);
        model.addAttribute("post", post);

        return "community/commUpdate";
    }

    @RequestMapping("commUpdate")
    public String commUpdate(HttpServletRequest request, Model model) {


        int trNo = Integer.parseInt(request.getParameter("trNo"));
        String cateCd = request.getParameter("cateCd");
        String trSubject = request.getParameter("trSubject");
        String trContent = request.getParameter("trContent");


//        commService.commUpdate(trNo, cateCd, trSubject, trContent, new Date());


        TrendVO trendVO = commService.findById(trNo);

        trendVO.setTrNo(trNo);
        trendVO.setCateCd(cateCd);
        trendVO.setTrSubject(trSubject);
        trendVO.setTrContent(trContent);
        trendVO.setTrUpdate(new Date());

        trendService.saveTrend(trendVO);

        return "redirect:commContent?trNo=" + trNo;
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
