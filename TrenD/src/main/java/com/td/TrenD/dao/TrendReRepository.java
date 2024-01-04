package com.td.TrenD.dao;

import com.td.TrenD.model.TrendReVO;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface TrendReRepository extends JpaRepository<TrendReVO, Integer> {
	@Query("select tr from TrendReVO tr where tr.trNo=:trNo")
	Page<TrendReVO> findByTrNo(@Param("trNo") int trNo, Pageable pageable);

//	@Query("select count(*) from TrendReVO tr where tr.trReDelYn='n'")
//	List<TrendReVO> count(@Param("trNo") int trNo);
}
