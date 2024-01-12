/**
 * 작업자 : 서준혁
 * 수정일자 : 2024-01-07
 * 수정내용
 * - URL RESTful API 방식으로 작성 : resources 복수 형태로 작성, 불필요한 경로 제거
 */
package com.td.TrenD.controller;

import com.td.TrenD.model.RePagingVO;
import com.td.TrenD.model.TrendReVO;
import com.td.TrenD.model.UserVO;
import com.td.TrenD.service.LoginService;
import com.td.TrenD.service.TrendReService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class TrendReController {

	private final TrendReService trendReService;
	private final LoginService loginService;

	//댓글 저장
	@PostMapping("/replies")
	public TrendReVO saveComment(@RequestBody final TrendReVO params, HttpSession session) {
		System.out.println("TrendReController.saveComment");

		//find User Instance
		UserVO userVO = loginService.checkUserId((String) session.getAttribute("userId"));

		return trendReService.saveReply(params, userVO);
	}

	// 댓글 리스트 조회
	@GetMapping("/replies")
	public Map<String, Object> findAllReply(final RePagingVO params) {
		System.out.println("TrendReController.findAllReply");
		Pageable pageable = PageRequest.of(
				params.getPage(),
				params.getItemPerPage(),
				Sort.by(
						Sort.Order.desc("trReRef"),
						Sort.Order.asc("trReNo")
				)
		);

		Page<TrendReVO> page = trendReService.findByTrNo(params, pageable);

		//예외처리
		if (page == null) return null;

		Map<String, Object> response = new HashMap<>();

		//페이징
		List<TrendReVO> content = page.getContent();
		int totalPages = page.getTotalPages();
		int currentPage = params.getPage() + 1;
		int pageListSize = params.getPageListSize();
		int startPage = (currentPage / pageListSize) * pageListSize + 1;
		int endPage = startPage + pageListSize - 1;

		if (endPage > totalPages) endPage = totalPages;

		response.put("content", content);
		response.put("currentPage", currentPage);
		response.put("totalPages", totalPages);
		response.put("pageListSize", pageListSize);
		response.put("startPage", startPage);
		response.put("endPage", endPage);

		return response;
	}

	// 댓글 상세정보 조회
	@GetMapping("/replies/{trReNo}")
	public TrendReVO findReplyById(@PathVariable final Integer trReNo) {
		System.out.println("TrendReController.findReplyById");
		return trendReService.findById(trReNo);
	}

	// 전체 댓글 개수 조회
	@GetMapping("/replies/count/{trNo}")
	public Integer CountAllReply(@PathVariable final Integer trNo) {
		System.out.println("TrendReController.CountAllReply");
		return trendReService.countAllReplyByTrNo(trNo);
	}

	// 기존 댓글 수정
	@PatchMapping("/replies")
	public TrendReVO updateReply(@RequestBody final TrendReVO params) {
		System.out.println("TrendReController.updateReply");
		trendReService.updateReply(params);
		return trendReService.findById(params.getTrReNo());
	}

	// 댓글 삭제
	@DeleteMapping("/replies/{trReNo}")
	public TrendReVO deleteReply(@PathVariable final Integer trReNo) {
		System.out.println("TrendReController.deleteReply");
		trendReService.deleteReply(trReNo);
		return trendReService.findById(trReNo);
	}
}