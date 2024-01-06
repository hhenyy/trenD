package com.td.TrenD.controller;

import com.td.TrenD.model.TrendVO;
import com.td.TrenD.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TestController {
    
    @Autowired
    private CommunityService commService;

    @RequestMapping("/")
    public String test(Model model) {

        List<TrendVO> commList = new ArrayList<>();

        commList = commService.commList();

        model.addAttribute("commList", commList);

        return "community/commTest";
    }
}
