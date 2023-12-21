package com.td.TrenD.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {

    @RequestMapping("/join")
    public String login(){
        System.out.println("회원가입폼 들어옴");
        return "login/join";
    }
}
