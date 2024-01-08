package com.td.TrenD.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.td.TrenD.dao.CategoryDao;
import com.td.TrenD.model.CategoryVO;

@Service
public class CategoryService {
	
	@Autowired
	CategoryDao dao;

	public Optional<CategoryVO> getCategory(String cate) {

		return dao.findById(cate);
	}

	public List<CategoryVO> get() {

		return dao.findAll();
	}
	
	

}
