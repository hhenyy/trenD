package com.td.TrenD.dao;

import com.td.TrenD.model.TrendVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrendRepository extends JpaRepository<TrendVO, Integer> {

//    List<TrendVO> findByTrSubjectContaining(String keyword);

//    @Query("select c from TrendVO c where LOWER(c.trSubject) like LOWER(concat('%', :keyword, '%')) AND c.categoryVO.cateCd <> :cateCd order by c.trNo desc")
//    Page<TrendVO> commSearchResult(@Param("keyword") String keyword, @Param("cateCd") String cateCd , Pageable pageable);

//    Page<TrendVO> findByTrSubjectContainingIgnoreCaseAndCategoryVO_CateCdNot(String keyword, String cateCd, Pageable pageable);

//    @Query("SELECT c FROM TrendVO c WHERE LOWER(c.trSubject) LIKE LOWER(concat('%', :keyword, '%')) AND c.categoryVO.cateCd <> :cateCd ORDER BY c.trNo DESC")
//    Page<TrendVO> commSearchResult(@Param("keyword") String keyword, @Param("cateCd") String cateCd, Pageable pageable);

//    @Query(value = "SELECT * FROM trend_tbl WHERE LOWER(trSubject) LIKE LOWER(CONCAT('%', :keyword, '%')) AND trend_tbl.cateCd <> :cateCd ORDER BY trNo DESC", nativeQuery = true)
//    Page<TrendVO> commSearchResult(@Param("keyword") String keyword, @Param("cateCd") String cateCd, Pageable pageable);


    @Query("SELECT c FROM TrendVO c WHERE c.trSubject LIKE %:keyword% AND c.categoryVO.cateCd <> :cateCd ORDER BY c.trNo DESC")
    Page<TrendVO> commSearchResult(@Param("keyword") String keyword, @Param("cateCd") String cateCd, Pageable pageable);

    @Query("SELECT c FROM TrendVO c WHERE c.trSubject LIKE %:keyword% AND c.categoryVO.cateCd = :cateCd ORDER BY c.trNo DESC")
    Page<TrendVO> trendSearchResult(@Param("keyword") String keyword, @Param("cateCd") String cateCd, Pageable pageable);

    int countTrendVOByCateCdContainingIgnoreCaseAndTrSubjectContaining(String cateCd, String Keyword);

    int countTrendVOByCateCdNotContainingIgnoreCaseAndTrSubjectContaining(String cateCd, String keyword);



}
