package com.td.TrenD.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.td.TrenD.model.AgeVO;
import com.td.TrenD.model.GenderVO;
import com.td.TrenD.model.LocationVO;
import com.td.TrenD.model.UserVO;
import com.td.TrenD.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    // 로그인 , 로그아웃 시 메인화면으로
    @RequestMapping("/loginout_ok")
    public String loginSuc(){
        return "main/main";
    }

    @RequestMapping("/loginform")
    public String login(){
        System.out.println("로그인 들어옴");
        return "login/login";
    }

    @RequestMapping("/joinform")
    public String join(){
        System.out.println("회원가입폼 들어옴");
        return "login/join";
    }

    @RequestMapping("/selectAge")
    @ResponseBody
    public List<AgeVO> selectAge(){
        System.out.println("회원가입폼에서 select문");

        List<AgeVO> ageVOList = loginService.selectAge();
        System.out.println("값"+ageVOList);
        return ageVOList;
    }

    @RequestMapping("/selectGender")
    @ResponseBody
    public List<GenderVO> selectGender(){

        List<GenderVO> genderVOList = loginService.selectGender();
        System.out.println("값"+genderVOList);
        return genderVOList;
    }

    @RequestMapping("/selectLoc")
    @ResponseBody
    public List<LocationVO> selectLoc(){
        System.out.println("회원가입폼에서 select문");

        List<LocationVO> locVOList = loginService.selectLoc();
        System.out.println("값"+locVOList);
        return locVOList;
    }

    @RequestMapping("/checkUserId")
    @ResponseBody
    public int checkId(@RequestParam("memid") String userId){
        System.out.println("아이디체크");
        System.out.println("들어온 아이디값"+userId);
        int data = 0;
        data = loginService.checkId(userId);
        System.out.println("데이타"+data);
        return data;
    }

    @RequestMapping("/checkUserNick")
    @ResponseBody
    public int checkUserNick(@RequestParam("memnick") String userName){
        System.out.println("닉네임체크");
        System.out.println("들어온 닉네임값"+userName);
        int data = loginService.checkNick(userName);
        System.out.println("닉네임데이터"+data);
        return data;
    }

    @RequestMapping("/insertUser")
    public String insertUser(@ModelAttribute UserVO user , Model model){

        user.setUserDate(new Date());
        user.setUserUpdate(new Date());
        user.setUserDelYn('n');

        UserVO result = loginService.insert(user);
        System.out.println("result:" + result);

        return "redirect:/loginform";
    }

    // 로그인 체크
    @RequestMapping("/checkLogin")
    public String checkLogin(@ModelAttribute UserVO user, HttpSession session ,Model model){
        System.out.println("입력한 userId"+user.getUserId());
        System.out.println("입력한 userPw"+user.getUserPw());
        int result;
        UserVO u = loginService.checkUserId(user.getUserId());

        // 1.아이디가 없을 경우
        if(u == null){
            result = 1;
            model.addAttribute("result",result);
            return "login/loginResult";
        // 2. 탈퇴한 회원일 경우
        }else if(u.getUserDelYn() == 'y'){
            result = 2;
            model.addAttribute("result",result);
            return "login/loginResult";
        } else {
            // 로그인 성공
            if(user.getUserPw().equals(u.getUserPw())){
                session.setAttribute("userId",u.getUserId());
                session.setAttribute("userName", u.getUserName());
                System.out.println("로그인 성공");
                return "redirect:/loginout_ok";
            // 비밀번호가 맞지 않아 로그인 실패
            }else{
                result = 3;
                model.addAttribute("result",result);
                return "login/loginResult";
            }
        }
    }

    // 로그아웃
    @RequestMapping("/logOut")
    public String goLogout(HttpSession session) {
        session.invalidate();
        // 홈으로 이동
        return "redirect:/loginout_ok";

    }

}
