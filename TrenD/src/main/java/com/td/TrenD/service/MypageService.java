package com.td.TrenD.service;

import com.td.TrenD.dao.MypageRepository;
import com.td.TrenD.model.TrendReVO;
import com.td.TrenD.model.TrendVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class MypageService {

    @Autowired
    private MypageRepository mypageRepository;

    public long getBoardCount() {
        return mypageRepository.countTrendVO();
    }

    public long getReplyCount() {
        return mypageRepository.countTrendReVO();
    }

    public Page<TrendVO> getBoardList(PageRequest pageable) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = userDetails.getUsername();
        return mypageRepository.findBoardListByUserId(userId, pageable);
    }

    public Page<TrendReVO> getReplyList(PageRequest pageable) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = userDetails.getUsername();
        return mypageRepository.findReplyListByUserId(userId, pageable);
    }
}