package com.td.TrenD.dao;

import com.td.TrenD.model.TrendVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TrendRepository extends JpaRepository<TrendVO, Integer> {

    @Query("SELECT t FROM TrendVO t " +
            "JOIN CategoryVO c ON t.category.cateCd = c.cateCd " +
            "where c.cateCd = 't'"+
            "ORDER BY t.trNo DESC")
    Page<TrendVO> findTrendList(Pageable pageable);

    @Query("SELECT t FROM TrendVO t " +
            "JOIN CategoryVO c ON t.category.cateCd = c.cateCd " +
            "WHERE c.cateCd = 't' AND t.trSubject LIKE CONCAT('%', :keyword, '%') " +
            "ORDER BY t.trNo DESC")
    Page<TrendVO> searchTrendList(@Param("keyword") String keyword, Pageable pageable);

}
