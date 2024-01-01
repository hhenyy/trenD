package com.td.TrenD.service;

import com.td.TrenD.dao.TrendRepository;
import com.td.TrenD.model.TrendVO;
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


    public TrendVO saveTrend(TrendVO trendVO) {
        return trendRepository.save(trendVO);
    }

//    public List<TrendVO> searchTrend(String keyword){
//        return trendRepository.findByTrSubjectContaining(keyword);
//    }


    public Page<TrendVO> commSearchResult(String keyword, String cateCd, Pageable pageable){
        return trendRepository.commSearchResult(keyword, cateCd, pageable);
    }
    public Page<TrendVO> trendSearchResult(String keyword, String cateCd, Pageable pageable){
        return trendRepository.trendSearchResult(keyword, cateCd, pageable);
    }

//    public Page<TrendVO> commSearchResult(String keyword, String cateCd, Pageable pageable){
//        return trendRepository.findByTrSubjectContainingIgnoreCaseAndCategoryVO_CateCdNot(keyword, cateCd, pageable);
//    }

public int commSearchResultCount(String cateCd, String keyword){
        return trendRepository.countTrendVOByCateCdNotContainingIgnoreCaseAndTrSubjectContaining(cateCd, keyword);
}
public int trendSearchResultCount(String cateCd, String keyword){
        return trendRepository.countTrendVOByCateCdContainingIgnoreCaseAndTrSubjectContaining(cateCd, keyword);
}

}