package com.td.TrenD.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.td.TrenD.model.GenderVO;

public interface GenderDao extends JpaRepository<GenderVO, Integer> {

	public List<GenderVO> findAll();
	
	@Query(value = "select genNm from (select s.staNo, s.userId, c.cateNm " +
            "from statistics s " +
            "left join trend_tbl t on s.trNo = t.trNo " +
            "left join category_code c on t.cateCd = c.cateCd) A " +
            "left join user u on A.userId = u.userId " +
            "left join gender_code g on u.genCd=g.genCd " +
            "where cateNm = :cateNm", nativeQuery = true)
	List<String> countGender(@Param("cateNm") String cateNm);
	//세 개의 테이블을 조인하여 그 갯수를 세는 메소드가 필요

}