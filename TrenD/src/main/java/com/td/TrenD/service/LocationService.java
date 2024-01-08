package com.td.TrenD.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.td.TrenD.dao.LocationDao;
import com.td.TrenD.model.LocationVO;

@Service
public class LocationService {

	@Autowired
	LocationDao dao;
	
	public List<String> getList() {

		List<LocationVO> locations = dao.findAll();
		return locationName(locations);
	}

	private List<String> locationName(List<LocationVO> locations) {

		return locations.stream()				//가져온 LocationVO 리스트를 스트림으로 변환
		.map(LocationVO::getLocNm)		//.map을 통해 리스트 내부의 요소 각각에 평가식 적용. 현재는 LocationVO의 게터 메소드를 사용하여 값을 대체
		.collect(Collectors.toList());	//스트림을 다시 리스트로 변환
	}

	public List<String> countLocation(String cateNm) {

		return dao.countLocation(cateNm);
	}

}
