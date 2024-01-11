package com.td.TrenD.service;

import com.td.TrenD.dao.AgeDao;
import com.td.TrenD.model.AgeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgeService {
	
	@Autowired
	AgeDao dao;

	public List<String> getList() {
		List<AgeVO> ages = dao.findAll();
		return ageName(ages);	//AgeVO의 모든 데이터를 찾아온 후 ageName 클래스로 전달
	}

	private List<String> ageName(List<AgeVO> ages) {	
		return ages.stream()							//전달된 ages 리스트를 스트림으로 변환
		.map(age -> age.getAgeNm())						//.map은 스트림 내 요소들 각각(age란 이름으로 설정)에 평가식을 적용시켜 변환시킴
		//.map(AgeVO::getAgeNm)							//바로 위의 코드와 동일한 효과. ::는 왼쪽 객체 내부의 메소드를 사용한다는 의미
														//이렇게 생성된 객체는 전부 기존의 age를 대체하게 됨. 각 칸에 ageNm값이 존재
		.collect(Collectors.toList());					//새롭게 변환된 스트림을 다시 리스트로 변환시킴
	}
	
	public List<String> countAge(String cateNm) {

		return dao.countAge(cateNm);
	}
	

}
