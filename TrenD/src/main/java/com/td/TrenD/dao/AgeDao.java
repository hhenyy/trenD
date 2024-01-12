package com.td.TrenD.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.td.TrenD.model.AgeVO;

public interface AgeDao extends JpaRepository<AgeVO, Integer> {
	//제네릭의 앞에는 이용할 엔티티가, 뒤에는 해당 엔티티의 고유키 자료형이 들어감

	public List<AgeVO> findAll();
	//JpaRepository에 의해 자동으로 생성되는 메소드. 맵핑된 엔티티의 프로퍼티명을 통한 검색
	
	@Query(value = "select ageNm from (select s.staNo, s.userId, c.cateNm " +
            "from statistics s " +
            "left join trend_tbl t on s.trNo = t.trNo " +
            "left join category_code c on t.cateCd = c.cateCd) A " +
            "left join user u on A.userId = u.userId " +
            "left join age_code a on u.ageCd = a.ageCd " +
            "where cateNm = :cateNm", nativeQuery = true)
	List<String> countAge(@Param("cateNm") String cateNm);
	//세 개의 테이블을 조인하여 그 갯수를 세는 메소드가 필요
	
}
