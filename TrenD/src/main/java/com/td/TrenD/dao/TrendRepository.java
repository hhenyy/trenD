package com.td.TrenD.dao;

import com.td.TrenD.model.TrendVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrendRepository extends JpaRepository<TrendVO, Integer> {

    List<TrendVO> findByTrSubjectContaining(String keyword);

}
