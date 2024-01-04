package com.td.TrenD.dao;

import com.td.TrenD.model.CategoryVO;
import com.td.TrenD.model.TrendVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityRepository extends JpaRepository<TrendVO,Integer> {

    @Query("select c from CategoryVO c where c.cateCd <> 't' order by c.idx_category")
    List<CategoryVO> findAllCategory();

    @Query("SELECT t FROM TrendVO t " +
            "JOIN CategoryVO c ON t.category.cateCd = c.cateCd " +
            "where c.cateCd <> 't' AND t.trDelYn = 'n'"+
            "ORDER BY t.trNo DESC")
    Page<TrendVO> findCommList(Pageable pageable);

    @Query("SELECT t FROM TrendVO t " +
            "JOIN CategoryVO c ON t.category.cateCd = c.cateCd " +
            "where c.cateCd = :cateCd AND t.trDelYn = 'n'"+
            " ORDER BY t.trNo DESC")
    Page<TrendVO> findCategoryList(@Param("cateCd") String cateCd ,Pageable pageable);

    @Query("SELECT t FROM TrendVO t " +
            "JOIN CategoryVO c ON t.category.cateCd = c.cateCd " +
            "WHERE c.cateCd <> 't' AND t.trDelYn = 'n' " +
            "AND CASE " +
            "   WHEN :search = 'trSubject' THEN LOWER(t.trSubject) " +
            "   WHEN :search = 'userId' THEN LOWER(t.userId) " +
            "   WHEN :search = 'trContent' THEN LOWER(t.trContent) " +
            "   ELSE '' END LIKE CONCAT('%', LOWER(:keyword), '%') " +
            "ORDER BY t.trNo DESC")
    Page<TrendVO> searchCommList(@Param("keyword") String keyword,
                                 @Param("search") String search, Pageable pageable);



}
