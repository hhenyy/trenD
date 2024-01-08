package com.td.TrenD.dao;

import com.td.TrenD.model.TrendReVO;
import com.td.TrenD.model.TrendVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MypageRepository extends JpaRepository<TrendVO, Integer> {

    @Query("SELECT COUNT(t) FROM TrendVO t")
    long countTrendVO();

    @Query("SELECT COUNT(tr) FROM TrendReVO tr")
    long countTrendReVO();

    @Query("SELECT t FROM TrendVO t " +
            "JOIN CategoryVO c ON t.categoryVO.cateCd = c.cateCd " +
            "WHERE t.userVO.userId = :userId AND t.trDelYn = 'n' " +
            "ORDER BY t.trNo DESC")
    Page<TrendVO> findBoardListByUserId(@Param("userId") String userId, Pageable pageable);

    @Query("SELECT tr FROM TrendReVO tr " +
            "WHERE tr.userVO.userId = :userId AND tr.trReDelYn = 'n' " +
            "ORDER BY tr.trReNo DESC")
    Page<TrendReVO> findReplyListByUserId(@Param("userId") String userId, Pageable pageable);

    @Query("SELECT t FROM TrendVO t WHERE t.trDelYn = 'n' ORDER BY t.trNo DESC")
    Page<TrendVO> findBoardList(Pageable pageable);

    @Query("SELECT tr FROM TrendReVO tr WHERE tr.trReDelYn = 'n' ORDER BY tr.trReNo DESC")
    Page<TrendReVO> findReplyList(Pageable pageable);

    @Query("SELECT t FROM TrendVO t WHERE t.trDelYn = 'n' ORDER BY t.trNo DESC")
    Page<TrendVO> findAllBoardForAdmin(Pageable pageable);

    @Query("SELECT tr FROM TrendReVO tr WHERE tr.trReDelYn = 'n' ORDER BY tr.trReNo DESC")
    Page<TrendReVO> findAllReplyForAdmin(Pageable pageable);

    @Query("SELECT tr FROM TrendReVO tr WHERE tr.trNo = :trNo")
    List<TrendReVO> findReplyByTrNo(@Param("trNo") int trNo);


}
