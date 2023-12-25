package com.td.TrenD.controller;

import com.td.TrenD.model.CategoryVO;
import com.td.TrenD.model.TrendVO;
//import com.td.TrenD.commService.CommunityService;
import com.td.TrenD.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CommunityController {

    @Autowired
    private CommunityService commService;

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
        System.out.println(categoryList);

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


        TrendVO result = new TrendVO();
        result = commService.commInsert(comm);
        System.out.println(result);


        return "/commTest";
    }

    @RequestMapping("commContent")
    public String commContent(HttpServletRequest request, Model model){

        TrendVO content = new TrendVO();

        int trNo = Integer.parseInt(request.getParameter("No"));

        content = commService.commContent(trNo);

        model.addAttribute("content", content);

        return "community/commContent";
    }


}
