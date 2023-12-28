package com.td.TrenD.service;

import com.td.TrenD.dao.TrendRepository;
import com.td.TrenD.model.TrendVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class TrendService {

    @Autowired
    private TrendRepository trendRepository;

    public Page<TrendVO> getTrendList(PageRequest pageable) {
        return trendRepository.findTrendList(pageable);
    }

}
