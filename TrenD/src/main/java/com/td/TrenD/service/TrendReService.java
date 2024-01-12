/**
 * 작업자 : 서준혁
 * 수정일자 : 2024-01-05
 * 설명 : 댓글 CRUD 관련 Service Class
 */

package com.td.TrenD.service;

import com.td.TrenD.dao.TrendReRepository;
import com.td.TrenD.model.AgeVO;
import com.td.TrenD.model.RePagingVO;
import com.td.TrenD.model.TrendReVO;
import com.td.TrenD.model.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrendReService {
	private final TrendReRepository trendReRepository;

	/**
	 * 댓글 저장
	 * @param params
	 * @param userVO
	 * @return TrendReVO
	 */
	public TrendReVO saveReply(TrendReVO params, UserVO userVO) {
		System.out.println("TrendReService.saveReply");

		//댓글 - ref x, level 0
		//대댓글 - ref o, level 1
		if (params.getTrReRef() != null) params.setTrReLev(1);
		else params.setTrReLev(0);

		//공통
		params.setUserVO(userVO);
		params.setTrReDate(new Timestamp(System.currentTimeMillis()));
		params.setTrReUpdate(new Timestamp(System.currentTimeMillis()));
		params.setTrReDelYn('n');
		TrendReVO save = trendReRepository.save(params);

		//댓글
		if (params.getTrReRef() == null){
			save.synchronizeRefWithTrReNo();
			trendReRepository.save(save);
		}

		return save;
	}

	/**
	 * 댓글 목록 조회
	 * @param params
	 * @param pageable
	 * @return Page<TrendReVO>
	 */
	public Page<TrendReVO> findByTrNo(RePagingVO params, Pageable pageable) {
		System.out.println("TrendReService.findByTrNo");
		return trendReRepository.findByTrNo(params.getTrNo(), pageable);
	}

	/**
	 * 댓글 개수 조회
	 * @param trNo
	 * @return Integer
	 */
	//
	public Integer countAllReplyByTrNo(int trNo) {
		System.out.println("TrendReService.countAllReplyByTrNo");
		return trendReRepository.countAllReplyByTrNo(trNo);
	}

	/**
	 * 댓글 객체 조회
	 * @param trReNo
	 * @return TrendReVO
	 */
	public TrendReVO findById(Integer trReNo) {
		System.out.println("TrendReService.findById");
		return trendReRepository.findById(trReNo).orElse(null);
	}

	/**
	 * 댓글 수정
	 * @param params
	 */
	public void updateReply(TrendReVO params) {
		System.out.println("TrendReService.updateReply");
		Optional<TrendReVO> reVO = trendReRepository.findById(params.getTrReNo());
		reVO.ifPresent(r -> {
			r.setTrReContent(params.getTrReContent());
			r.setTrReUpdate(new Timestamp(System.currentTimeMillis()));
			trendReRepository.save(r);
		});
	}

	/**
	 * 댓글 삭제
	 * @param trReNo
	 */
	public void deleteReply(Integer trReNo) {
		System.out.println("TrendReService.deleteReply");
		Optional<TrendReVO> reVO = trendReRepository.findById(trReNo);
		reVO.ifPresent(r -> {
			r.setTrReDelYn('y');
			trendReRepository.save(r);
		});
	}

	public List<String> findAgeList(int trNo) {

		return trendReRepository.findAgeList(trNo);
	}

	public List<String> findLocationList(int trNo) {

		return trendReRepository.findLocationList(trNo);
	}

	public List<String> findGenderList(int trNo) {

		return trendReRepository.findGenderList(trNo);
	}

	
}
