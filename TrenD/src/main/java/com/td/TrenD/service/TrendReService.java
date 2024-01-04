package com.td.TrenD.service;

import com.td.TrenD.dao.TrendReRepository;
import com.td.TrenD.model.RePagingVO;
import com.td.TrenD.model.TrendReVO;
import com.td.TrenD.model.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrendReService {
	private final TrendReRepository trendReRepository;

	public Page<TrendReVO> findByTrNo(RePagingVO params, Pageable pageable) {
		System.out.println("TrendReService.findByTrNo");
		return trendReRepository.findByTrNo(params.getTrNo(), pageable);
	}

	//저장 후 trReNo 반환
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

	public TrendReVO findById(Integer trReNo) {
		System.out.println("TrendReService.findById");
		return trendReRepository.findById(trReNo).orElse(null);
	}

	public void updateReply(TrendReVO params) {
		System.out.println("TrendReService.updateReply");
		Optional<TrendReVO> reVO = trendReRepository.findById(params.getTrReNo());
		reVO.ifPresent(r -> {
			r.setTrReContent(params.getTrReContent());
			r.setTrReUpdate(new Timestamp(System.currentTimeMillis()));
			trendReRepository.save(r);
		});
	}

	public void deleteReply(Integer trReNo) {
		System.out.println("TrendReService.deleteReply");
		Optional<TrendReVO> reVO = trendReRepository.findById(trReNo);
		reVO.ifPresent(r -> {
			r.setTrReDelYn('y');
			trendReRepository.save(r);
		});
	}
}
