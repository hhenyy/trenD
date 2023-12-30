package com.td.TrenD.service;

import com.td.TrenD.dao.MypageRepository;
import com.td.TrenD.model.TrendReVO;
import com.td.TrenD.model.TrendVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public Page<TrendVO> getBoardList(PageRequest pageable, String loggedInUserId) {
        if (isAdmin()) {
            System.out.println("관리자 게시글");
            return mypageRepository.findAllBoardForAdmin(pageable);
        } else {
            System.out.println("유저 게시글");
            return mypageRepository.findBoardListByUserId(loggedInUserId, pageable);
        }
    }

    public Page<TrendReVO> getReplyList(PageRequest pageable, String loggedInUserId) {
        if (isAdmin()) {
            System.out.println("관리자 댓글");
            return mypageRepository.findAllReplyForAdmin(pageable);
        } else {
            System.out.println("유저 댓글");
            return mypageRepository.findReplyListByUserId(loggedInUserId, pageable);
        }
    }

    public boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName(); // 현재 로그인한 사용자의 아이디

        // userId가 'admin'이 아닌 경우에만 데이터베이스 조회
        return !"admin".equals(userId);
    }

    public Page<TrendReVO> getReplyListForAdmin(PageRequest pageable) {
        return mypageRepository.findAllReplyForAdmin(pageable);
    }
}
