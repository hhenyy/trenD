package com.td.TrenD.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.td.TrenD.dao.TrendDao;
import com.td.TrenD.model.TrendVO;

@Service
public class TrendService {
	
	@Autowired
	TrendDao dao;

	public Optional<TrendVO> getCate(Integer c) {

		return dao.findById(c);
	}
}
