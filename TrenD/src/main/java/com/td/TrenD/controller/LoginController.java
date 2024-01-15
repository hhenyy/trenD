package com.td.TrenD.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.td.TrenD.model.AgeVO;
import com.td.TrenD.model.GenderVO;
import com.td.TrenD.model.LocationVO;
import com.td.TrenD.model.UserVO;
import com.td.TrenD.service.LoginService;
import com.td.TrenD.service.tempKey;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    @RequestMapping("/editUserForm")
    public String editUser(@SessionAttribute("userId") String id , Model model){
        System.out.println("수정회원폼 들어옴");
        System.out.println(id);

        UserVO user = loginService.checkUserId(id);

        List<AgeVO> ageVOList = loginService.selectAge();
        List<GenderVO> genderVOList = loginService.selectGender();
        List<LocationVO> locVOList = loginService.selectLoc();

        model.addAttribute("user",user);
        model.addAttribute("ageList",ageVOList);
        model.addAttribute("genderList",genderVOList);
        model.addAttribute("locList",locVOList);

        return "/login/editUser";
    }

    @RequestMapping("/updateUser")
    public String updateUser(@ModelAttribute UserVO user, @RequestParam("ageCd") String ageCd, @RequestParam("locCd") String locCd, @RequestParam("genCd") String genCd ,Model model){

        AgeVO age = new AgeVO();
        age.setAgeCd(ageCd);
        user.setAgeVO(age);

        LocationVO loc = new LocationVO();
        loc.setLocCd(locCd);
        user.setLocationVO(loc);

        GenderVO gen = new GenderVO();
        gen.setGenCd(genCd);
        user.setGenderVO(gen);
        System.out.println(user.getAgeVO());
        System.out.println("수정");
        System.out.println("userVO" + user);
        System.out.println(user.getUserDate());
        user.setUserUpdate(new Date());

        loginService.updateUser(user);

        return "/login/editUserOk";
    }

    @RequestMapping("/editPw")
    public String editPw(){
        return "/login/editPw";
    }

    @RequestMapping("/editPwOk")
    public String editPwOk(@RequestParam("userId") String userId, @RequestParam("userPw") String userChgPw){
        System.out.println("암호"+userChgPw);
        System.out.println("아이디"+userId);

        UserVO user = loginService.checkUserId(userId);

        user.setUserUpdate(new Date());
        user.setUserPw(userChgPw);

        loginService.updateUser(user);

        return "login/editPwOk";
    }

    @RequestMapping("/delUser")
    public String delUserForm(){
        return "login/delUser";
    }

    @RequestMapping("/delCheckOk")
    public String delUserOk(@RequestParam("userId") String userId, HttpSession session){
        loginService.deleteUser(userId);
        session.invalidate();
        return "login/delUserOk";
    }

    @RequestMapping("/findId")
    public String findId(){
        return "login/findId";
    }

    // 아이디 찾기
    @RequestMapping(value = "findIdCheck", method = RequestMethod.POST)
    public String fIdCheck(UserVO user, Model model) throws Exception {

        Optional<UserVO> u = loginService.findId(user.getUserName(), user.getUserEmail());

        if(u.isPresent()){
            String id = u.get().getUserId();
            model.addAttribute("findId", id);
            return "login/findId";
        }else{
            return "login/findIdFail";
        }
    }

    @RequestMapping("/findPw")
    public String findPw(){
        return "login/findPw";
    }

    @RequestMapping("/findPwCheck")
    public String findPwCheck(UserVO user, Model model) throws  Exception{

        UserVO u = loginService.checkUserId(user.getUserId());

        // 1. 조회한 회원이 없을 때
        if(u == null){
            return "login/findFail";
        // 2. 탈퇴한 회원인 경우
        }else if(u.getUserDelYn() == 'y'){
            return "login/findFail";
        // 3. 조회한 회원이 있는 경우
        } else {
            loginService.updatePw(u);
            model.addAttribute("findPw",1);
            return "login/findPw";
        }

    }

    @RequestMapping("/naverCallback")
    public String naverLogin(){
        System.out.println("네네이버 로그인 들어옴");
        return "login/loginNaver";
    }

    @RequestMapping("/joinNaverUser")
    @ResponseBody
    public String joinNaverUser(@RequestParam("userId") String userId,
                                @RequestParam("userName") String userName,
                                @RequestParam("userEmail") String userEmail,
                                UserVO user,
                                HttpSession session){

        user.setUserId(userId);
        // 랜덤 문자열을 생성해 임시 비밀번호 저장
        String userPw = new tempKey().getKey(8,false);
        user.setUserPw(userPw);
        user.setUserName(userName);
        user.setUserEmail(userEmail);
        user.setUserDate(new Date());
        user.setUserUpdate(new Date());
        user.setUserDelYn('n');

        UserVO chkUser = loginService.checkUserId(userId);

        // 1. 연동기록이 없는 최초의 가입 일 경우
        if(chkUser == null){
            // 회원 가입 실패
            if(loginService.insert(user) == null) {
                return "E";
            // 회원 가입 성공
            } else {
                System.out.println("최초가입");
                session.setAttribute("userId", userId);
                session.setAttribute("userName", userName);
                return "J";
            }
        // 2. 탈퇴한 회원 인 경우
        } else if(chkUser.getUserDelYn() == 'y'){
            return "N";
        // 3. 로그인 성공
        } else {
            System.out.println("네이버 로그인 성공");
            session.setAttribute("userId", userId);
            session.setAttribute("userName", userName);
            return "Y";
        }
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
    public String insertUser(@ModelAttribute UserVO user
                                            ,String ageCd
                                            ,String locCd
                                            ,String genCd
                                            ,Model model){

        AgeVO age = new AgeVO();
        age.setAgeCd(ageCd);
        user.setAgeVO(age);

        GenderVO gender = new GenderVO();
        gender.setGenCd(genCd);
        user.setGenderVO(gender);

        LocationVO loc = new LocationVO();
        loc.setLocCd(locCd);
        user.setLocationVO(loc);

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
