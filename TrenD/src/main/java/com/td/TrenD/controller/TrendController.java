package com.td.TrenD.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class TrendController {
	@RequestMapping("/post/view")
	public String openPostView(@RequestParam final Integer trNo, Model model, HttpSession session) {
		System.out.println("TrendReController.openPostView");
		model.addAttribute("trNo", trNo);
		session.setAttribute("id", "jun");
		return "trendRe/trendContent";
	}

	@RequestMapping("/")
	public String start() {
		System.out.println("TrendController.start");
		return "sample_css/css_test";
	}
}
