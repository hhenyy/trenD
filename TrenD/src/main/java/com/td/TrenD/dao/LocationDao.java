package com.td.TrenD.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.td.TrenD.model.LocationVO;

public interface LocationDao extends JpaRepository<LocationVO, Integer> {

	public List<LocationVO> findAll();
	
	@Query(value = "select locNm from (select s.staNo, s.userId, c.cateNm " +
            "from statistics s " +
            "left join trend_tbl t on s.trNo = t.trNo " +
            "left join category_code c on t.cateCd = c.cateCd) A " +
            "left join user u on A.userId = u.userId " +
            "left join location_code l on u.locCd=l.locCd " +
            "where cateNm = :cateNm", nativeQuery = true)
	List<String> countLocation(@Param("cateNm") String cateNm);
	//세 개의 테이블을 조인하여 그 갯수를 세는 메소드가 필요

}