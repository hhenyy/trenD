package com.td.TrenD.dao;

import com.td.TrenD.model.StatisticsVO;
import com.td.TrenD.model.TrendVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticsDao extends JpaRepository<StatisticsVO, Integer> {


    @Query("SELECT s FROM StatisticsVO s WHERE s.userVO.userId = :userId AND s.trNo = :trNo")
    StatisticsVO findByUserIdAndTrNo(@Param("userId") String userId, @Param("trNo") int trNo);

}
