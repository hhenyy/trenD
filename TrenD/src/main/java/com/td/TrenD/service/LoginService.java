package com.td.TrenD.service;

import com.td.TrenD.dao.LoginDao;
import com.td.TrenD.model.AgeVO;
import com.td.TrenD.model.GenderVO;
import com.td.TrenD.model.LocationVO;
import com.td.TrenD.model.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class LoginService {

    @Autowired
    private LoginDao dao;

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
}
