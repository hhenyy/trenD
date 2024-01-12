/**
 * 작업자 : 서준혁
 * 수정일자 : 2024-01-05
 * 설명 : 댓글 Repository Class
 */

package com.td.TrenD.dao;

import com.td.TrenD.model.TrendReVO;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface TrendReRepository extends JpaRepository<TrendReVO, Integer> {
	//댓글 리스트 조회
	@Query("select tr from TrendReVO tr where tr.trNo = :trNo and (tr.trReLev <> 1 or tr.trReDelYn <> 'y')")
	Page<TrendReVO> findByTrNo(@Param("trNo") int trNo, Pageable pageable);

	//댓글 리스트 item 개수 count
	@Query("select count(*) from TrendReVO tr where tr.trNo = :trNo and (tr.trReLev <> 1 or tr.trReDelYn <> 'y')")
	Integer countAllReplyByTrNo(@Param("trNo") int trNo);

	@Query(value="select ageNm from trendRe t "
			+ "left join user u on t.userId=u.userId "
			+ "left join age_code a on u.ageCd = a.ageCd where trNo=:trNo", nativeQuery = true)
	List<String> findAgeList(@Param("trNo") int trNo);

	@Query(value="select locNm from trendRe t "
			+ "left join user u on t.userId=u.userId "
			+ "left join location_code l on u.locCd=l.locCd where trNo=:trNo", nativeQuery = true)
	List<String> findLocationList(@Param("trNo") int trNo);
	
	@Query(value="select genNm from trendRe t "
			+ "left join user u on t.userId=u.userId "
			+ "left join gender_code g on u.genCd=g.genCd where trNo=:trNo", nativeQuery = true)
	List<String> findGenderList(@Param("trNo") int trNo);
	
}
