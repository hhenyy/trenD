package com.td.TrenD.service;

import com.td.TrenD.dao.GenderDao;
import com.td.TrenD.model.GenderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenderService {
	
	@Autowired
	GenderDao dao;
	
	public List<String> getList() {

		List<GenderVO> genders = dao.findAll();
		return GenderName(genders);
	}

	private List<String> GenderName(List<GenderVO> genders) {

		return genders.stream()						//가져온 GenderVO 리스트를 스트림으로 변환
				.map(gender -> gender.getGenNm())	//.map을 통해 각 요소 gender에 평가식을 적용시킴. GenNm으로 변환
				.collect(Collectors.toList());		//완성된 스트림을 다시 리스트로 변환
	}



	public List<String> countGender(String cateNm) {

		return dao.countGender(cateNm);
	}

}
