package com.td.TrenD.dao;

import com.td.TrenD.model.CategoryVO;
import com.td.TrenD.model.TrendVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityRepository extends JpaRepository<TrendVO, Integer> {

    @Query("select c from CategoryVO c order by c.idx_category")
    List<CategoryVO> findAllCategory();

    @Query("select t from TrendVO t order by t.trNo desc")
    List<TrendVO> commList();

    @Query("select t from TrendVO t join t.categoryVO c where t.trNo = :trNo")
    TrendVO commContent(@Param("trNo") int trNo);
}
