package com.td.TrenD.controller;

import com.td.TrenD.model.CategoryVO;
import com.td.TrenD.model.TrendVO;
//import com.td.TrenD.commService.CommunityService;
import com.td.TrenD.service.CommunityService;
import com.td.TrenD.service.TrendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class CommunityController {

    @Autowired
    private CommunityService commService;

    @Autowired
    private TrendService trendService;

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
        result = trendService.trendSave(comm);
        System.out.println(result);


        return "redirect:/";
    }

    @RequestMapping("commContent")
    public String commContent(HttpServletRequest request, Model model) {



        TrendVO post = new TrendVO();
        int trNo = Integer.parseInt(request.getParameter("trNo"));

        post = commService.commContent(trNo);
        if(post.getTrDelYn()=='n') {
            int readCount = post.getTrReadCount() + 1;
            post.setTrReadCount(readCount);
            trendService.trendSave(post);
        }


//        char trDelYn = post.getTrDelYn();
//
//        if(trDelYn == 'y'){
//            model.addAttribute("trDelYn", 'y');
//        }

        model.addAttribute("post", post);

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

        trendService.trendSave(trendVO);

        return "redirect:commContent?trNo=" + trNo;
    }

    @RequestMapping("deletePost")
    public String deletePost(HttpServletRequest request, Model model){

        int trNo = Integer.parseInt(request.getParameter("trNo"));

        TrendVO trendVO = commService.findById(trNo);
        trendVO.setTrUpdate(new Date());
        trendVO.setTrDelYn('y');

        trendService.trendSave(trendVO);

        return "redirect:/";
    }

    @RequestMapping("totalSearch")
    public String searchTest(HttpServletRequest request, Model model){

        return "main/totalSearch";
    }

}
