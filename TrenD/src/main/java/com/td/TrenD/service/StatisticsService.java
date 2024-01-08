package com.td.TrenD.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.td.TrenD.dao.StatisticsDao;

@Service
public class StatisticsService {
	
	@Autowired
	private StatisticsDao dao;

//	public List<Integer> getTrNo() {
//
//		return dao.getTrNo();
//	}

	public int count(String categoryOpt) {
		
		return dao.count(categoryOpt);
	}


}
