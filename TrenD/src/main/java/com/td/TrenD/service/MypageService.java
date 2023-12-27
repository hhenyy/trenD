package com.td.TrenD.service;

import com.td.TrenD.dao.MypageRepository;
import com.td.TrenD.model.TrendReVO;
import com.td.TrenD.model.TrendVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        return mypageRepository.findBoardList(pageable);
    }

    public Page<TrendReVO> getReplyList(PageRequest pageable) {
        return mypageRepository.findReplyList(pageable);
    }
}