package com.td.TrenD.service;

import com.td.TrenD.dao.LoginDao;
import com.td.TrenD.model.AgeVO;
import com.td.TrenD.model.GenderVO;
import com.td.TrenD.model.LocationVO;
import com.td.TrenD.model.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LoginService {

    @Autowired
    private LoginDao dao;

    @Autowired
    JavaMailSender mailSender;

    public List<AgeVO> selectAge(){
        System.out.println("서비스들어왔어요");
        return dao.findSelectAgeAll();
    }

    public List<GenderVO> selectGender(){
        return dao.findSelectGenderAll();
    }

    public List<LocationVO> selectLoc(){
        return dao.findSelectLocAll();
    }

    public int checkId(String userId){
        return dao.checkId(userId);
    }

    public int checkNick(String userName){
        return dao.checkNick(userName);
    }

    public UserVO insert(UserVO user){
        return dao.save(user);
    }

    public UserVO checkUserId(String userId) {
        return dao.findByUserId(userId);
    }

    public void updateUser(UserVO user){
        dao.save(user);
    }

    public void deleteUser(String userId){
        dao.deleteUser(userId);
    }

    public Optional<UserVO> findId(String userName, String userEmail){
        return dao.findByUserNameAndUserEmail(userName, userEmail);
    }

    public void updatePw(UserVO user) throws Exception {
        // 1. 랜덤 문자열을 생성해 임시 비밀번호 저장
        String tempPw = new tempKey().getKey(8, false);

        // 초기화 메일 바롱
        mailHandler sendMail = new mailHandler(mailSender);
        sendMail.setSubject("[트렌D 임시 비밀번호 안내 메일입니다.]");
        sendMail.setText(
                "<h1>트렌D 임시 비밀번호 안내</ht>" +
                "<br> [임시 비밀번호] 로 로그인하신 후 비밀번호를 변경해주세요." +
                "<br> 임시 비밀번호 : "+tempPw
        );
        sendMail.setFrom("trendsendmail@naver.com", "트렌D");
        sendMail.setTo(user.getUserEmail());
        sendMail.send();
        
        // 임시 비밀번호로 변경
        System.out.println(user.getUserName());

        user.setUserPw(tempPw);
        user.setUserUpdate(new Date());
        dao.save(user);
    }
}
