package com.td.TrenD.service;


import com.td.TrenD.dao.StatisticsDao;
import com.td.TrenD.model.StatisticsVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class StatisticsService {

	@Autowired
    private final StatisticsDao statisticsDao;


    public StatisticsService(StatisticsDao statisticsDao) {
        this.statisticsDao = statisticsDao;
    }

    public StatisticsVO saveStatics(StatisticsVO statisticsVO){
        return statisticsDao.save(statisticsVO);
    }

//    public StatisticsVO checkStatics(String userId, int trNo){
//        return statisticsDao.findByUserIdAndTrNo(userId, trNo);
//    }
    
	public int count(String categoryOpt) {
		
		return statisticsDao.count(categoryOpt);
	}



}
