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

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

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
        if (isAdmin(loggedInUserId)) {
            System.out.println("관리자 게시글");
            return mypageRepository.findAllBoardForAdmin(pageable);
        } else {
            System.out.println("Logged in user ID: " + loggedInUserId);
            System.out.println("유저 게시글");
            return mypageRepository.findBoardListByUserId(loggedInUserId, pageable);
        }
    }

    public Page<TrendReVO> getReplyList(PageRequest pageable, String loggedInUserId) {
        if (isAdmin(loggedInUserId)) {
            System.out.println("관리자 댓글");
            return mypageRepository.findAllReplyForAdmin(pageable);
        } else {
            System.out.println("Logged in user ID: " + loggedInUserId);
            System.out.println("유저 댓글");
            return mypageRepository.findReplyListByUserId(loggedInUserId, pageable);
        }
    }


    public boolean isAdmin(String userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUserId = authentication.getName(); // 현재 로그인한 사용자의 아이디

        // userId가 'admin'인 경우에만 관리자로 판단
        return "admin".equals(userId);
    }

    public TrendVO getTrendInfoByTrNo(int trNo) {
        return mypageRepository.findById(trNo)
                .orElseThrow(() -> new EntityNotFoundException("Trend not found with trNo: " + trNo));
    }

    public Optional<TrendReVO> findReplyByTrNo(int trNo) {
        List<TrendReVO> replyList = mypageRepository.findReplyByTrNo(trNo);
        if (!replyList.isEmpty()) {
            return Optional.of(replyList.get(0));
        } else {
            return Optional.empty();
        }
    }

    public TrendReVO getReplyByTrNo(int trNo) {
        Optional<TrendReVO> replyOptional = findReplyByTrNo(trNo);
        return replyOptional.orElseThrow(() ->
                new EntityNotFoundException());
    }


}
