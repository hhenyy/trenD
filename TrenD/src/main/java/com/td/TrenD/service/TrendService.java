package com.td.TrenD.service;

import com.td.TrenD.dao.TrendRepository;
import com.td.TrenD.model.TrendVO;
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

    public TrendVO trendSave(TrendVO trendVO) {
        return trendRepository.save(trendVO);
    }

    public List<TrendVO> trendSearch(String keyword){
        return trendRepository.findByTrSubjectContaining(keyword);
    }



}