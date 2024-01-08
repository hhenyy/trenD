package com.td.TrenD.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.td.TrenD.dao.TrendDao;
import com.td.TrenD.model.TrendVO;

import com.td.TrenD.dao.TrendRepository;
import com.td.TrenD.model.TrendVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TrendService {

    private final TrendRepository trendRepository;

    public TrendService(TrendRepository trendRepository) {
        this.trendRepository = trendRepository;
    }

    public TrendVO trendContent(int trNo) {
        return trendRepository.trendContent(trNo);
    }
  
    public Page<TrendVO> getTrendList(PageRequest pageable) {
        return trendRepository.findTrendList(pageable);
    }

    public Page<TrendVO> searchTrendList(String keyword, PageRequest pageable) {
        return trendRepository.searchTrendList(keyword, pageable);
    }

    public TrendVO saveTrend(TrendVO trendVO) {
        return trendRepository.save(trendVO);
    }


    public Page<TrendVO> commSearchResult(String keyword, String cateCd, Pageable pageable){
        return trendRepository.commSearchResult(keyword, cateCd, pageable);
    }
    public Page<TrendVO> trendSearchResult(String keyword, String cateCd, Pageable pageable){
        return trendRepository.trendSearchResult(keyword, cateCd, pageable);
    }

public int commSearchResultCount(String cateCd, String keyword, char trDelYn){
        return trendRepository.countTrendVOByCateCdNotContainingIgnoreCaseAndTrSubjectContainingAndTrDelYn(cateCd, keyword, trDelYn);
}

public int trendSearchResultCount(String cateCd, String keyword, char trDelYn){
        return trendRepository.countTrendVOByCateCdContainingIgnoreCaseAndTrSubjectContainingAndTrDelYn(cateCd, keyword, trDelYn);
}
        
        public Optional<TrendVO> getCate(Integer c) {

    		return trendRepository.findById(c);

}

}