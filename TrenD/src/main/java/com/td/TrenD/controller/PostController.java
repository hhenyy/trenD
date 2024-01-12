package com.td.TrenD.controller;

import com.td.TrenD.model.CategoryVO;
import com.td.TrenD.model.StatisticsVO;
import com.td.TrenD.model.TrendReVO;
import com.td.TrenD.model.TrendVO;
import com.td.TrenD.model.UserVO;
import com.td.TrenD.service.AgeService;
import com.td.TrenD.service.CommunityService;
import com.td.TrenD.service.GenderService;
import com.td.TrenD.service.LocationService;
import com.td.TrenD.service.StatisticsService;
import com.td.TrenD.service.TrendReService;
import com.td.TrenD.service.TrendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PostController {

	@Autowired
	private CommunityService commService;

	@Autowired
	private TrendService trendService;

	@Autowired
	private TrendReService trendReService;

	@Autowired
	private StatisticsService staticsService;

	@Autowired
	private AgeService ageService;

	@Autowired
	private GenderService genderService;

	@Autowired
	private LocationService locationServce;

	@RequestMapping("post")
	public String commContent(HttpServletRequest request, HttpSession session, Model model) {

		TrendVO post = new TrendVO();
		int trNo = Integer.parseInt(request.getParameter("trNo"));

		post = trendService.trendContent(trNo);
		if (post.getTrDelYn() == 'n') {
			int readCount = post.getTrReadCount() + 1;
			post.setTrReadCount(readCount);
			trendService.saveTrend(post);

			UserVO user = new UserVO();
			TrendVO trend = new TrendVO();
			String userId = (String) session.getAttribute("userId");
			if (userId != null) { // 로그인이 된 상태라면
				StatisticsVO statics = new StatisticsVO(); // 통계 엔티티 생성
				statics = staticsService.checkStatics(userId, trNo);
				System.out.println("통계:" + statics);
				// 유저 아이디와 해당 게시글 글번호를 전송하여, 통계 테이블 내부에 두 정보를 전부 가진 데이터가 있는지를 확인
				// 즉, 유저가 최초 접속한 경우에만 통계와 관련된 정보가 생성되는 것. 그 후부터는 더 이상 x
				if (statics == null) {
					statics = new StatisticsVO();
					trend.setTrNo(trNo); // 글번호를 삽입
					statics.setTrendVO(trend); // 통계 엔티티에 글번호가 들어감
					user.setUserId(userId); // 유저 아이디를 삽입
					statics.setUserVO(user); // 통계 엔티티에 유저 아이디가 들어감
					staticsService.saveStatics(statics);
				}
			}
		}

		// 통계를 구합시다. 필요한 건 trNo.

		// select * from trendRe where trNo=1; 댓글 갯수
		int count = trendReService.countAllReplyByTrNo(trNo);

		List<String> aList = ageService.getList(); // 10대,20대,...
		List<String> gList = genderService.getList(); // 남자,여자
		// select ageNm from trendRe t
		// left join user u on t.userId=u.userId
		// left join age_code a on u.ageCd = a.ageCd where trNo=1; 댓글 단 사람들의 연령대
		// select locNm from trendRe t
		// left join user u on t.userId=u.userId
		// left join location_code l on u.locCd=l.locCd where trNo=1; 댓글 단 사람들의 지역
		// select genNm from trendRe t
		// left join user u on t.userId=u.userId
		// left join gender_code g on u.genCd=g.genCd where trNo=1; 댓글 단 사람들의 성별
		List<String> ageList = trendReService.findAgeList(trNo);
		List<String> genderList = trendReService.findGenderList(trNo);

		List<Object> list = new ArrayList<Object>();
		List<Object> AgeList = new ArrayList<Object>();
		List<Object> GenderList = new ArrayList<Object>();

		for (String a : aList) {
			int acount = Collections.frequency(ageList, a); // ageList 안에서 a의 갯수를 구함
			if (acount != 0) {
				list.add("'" + a + "'");
				list.add(acount); // list 안에는 v와 count 값이 들어가있음
				AgeList.add(list);
				list = new ArrayList<Object>();
			}
		}
		for (String g : gList) {
			int gcount = Collections.frequency(genderList, g); // ageList 안에서 a의 갯수를 구함
			if (gcount != 0) {
				list.add("'" + g + "'");
				list.add(gcount); // list 안에는 v와 count 값이 들어가있음
				GenderList.add(list);
				list = new ArrayList<Object>();
			}
		}

		model.addAttribute("post", post);
		model.addAttribute("age", AgeList);
		model.addAttribute("gender", GenderList);
		model.addAttribute("count", count);

		return "trend/trendContent";
	}

	@PostMapping("/reload/{trNo}") // 포스트 방식의 요청을 받는 어노테이션. 링크를 통해 전송된 값들은 /{}을 통해 받아야 한다
									//오로지 그래프 새로고침만을 위한 메소드. 위의 메소드와 대부분을 공유, 좀 더 효율적으로 수정할 수 있을 것
	@ResponseBody
	public ResponseEntity<Map<String, Object>> refresh(@PathVariable("trNo") int trNo) {
		System.out.println("이거 작동은 해?");
		
		int count = trendReService.countAllReplyByTrNo(trNo);

		List<String> aList = ageService.getList(); // 10대,20대,...
		List<String> gList = genderService.getList(); // 남자,여자
		List<String> ageList = trendReService.findAgeList(trNo);
		List<String> genderList = trendReService.findGenderList(trNo);

		List<Object> list = new ArrayList<Object>();
		List<Object> AgeList = new ArrayList<Object>();
		List<Object> GenderList = new ArrayList<Object>();

		for (String a : aList) {
			int acount = Collections.frequency(ageList, a); // ageList 안에서 a의 갯수를 구함
			if (acount != 0) {
				list.add(a);
				list.add(acount); // list 안에는 v와 count 값이 들어가있음
				AgeList.add(list);
				list = new ArrayList<Object>();
			}
		}
		for (String g : gList) {
			int gcount = Collections.frequency(genderList, g); // ageList 안에서 a의 갯수를 구함
			if (gcount != 0) {
				list.add(g);
				list.add(gcount); // list 안에는 v와 count 값이 들어가있음
				GenderList.add(list);
				list = new ArrayList<Object>();
			}
		}

		
		Map map = new HashMap<>();
		map.put("age2", AgeList);
		map.put("gender2", GenderList);
		map.put("count2", count); // 여러 값을 전송하게 되면 map 객체에 담아 전송해줘야 한다

		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	@RequestMapping("totalSearch")
	public String totalSearch(HttpServletRequest request, Model model) {

		model.addAttribute("keyword", request.getParameter("keyword"));

		return "main/totalSearch";
	}

	@RequestMapping("viewTrend")
	public String viewTrend(@RequestParam("trend") String trend) {

		TrendVO result = trendService.findTrend(trend);

		String trNo = Integer.toString(result.getTrNo());

		return "redirect:post?trNo=" + trNo;
	}

	@RequestMapping("commForm")
	public String commForm(Model model) {

		List<CategoryVO> categoryList = commService.findAllCategory();
		model.addAttribute("categoryList", categoryList);

		return "community/commForm";
	}

	@RequestMapping("commInsert")
	public String commInsert(HttpServletRequest request, HttpSession session) {

		String userId = (String) session.getAttribute("userId");

		TrendVO comm = new TrendVO();
		UserVO user = new UserVO();
		user.setUserId(userId);

		comm.setUserVO(user);
		comm.setCateCd(request.getParameter("cateCd"));
		comm.setTrSubject(request.getParameter("trSubject"));
		comm.setTrContent(request.getParameter("trContent"));

		comm.setTrDate(new Date());
		comm.setTrUpdate(new Date());
		comm.setTrDelYn('n');
		comm.setTrReadCount(0);

		TrendVO result = new TrendVO();
		result = trendService.saveTrend(comm);
		System.out.println(result);

		return "redirect:/";
	}

	@RequestMapping("commUpdateForm")
	public String commUpdateForm(HttpServletRequest request, Model model) {

		List<CategoryVO> categoryList = commService.findAllCategory();
		model.addAttribute("categoryList", categoryList);

		TrendVO post = new TrendVO();
		int trNo = Integer.parseInt(request.getParameter("trNo"));
		post = trendService.trendContent(trNo);
		model.addAttribute("post", post);

		return "community/commUpdate";
	}

	@RequestMapping("commUpdate")
	public String commUpdate(HttpServletRequest request) {

		int trNo = Integer.parseInt(request.getParameter("trNo"));
		String cateCd = request.getParameter("cateCd");
		String trSubject = request.getParameter("trSubject");
		String trContent = request.getParameter("trContent");

		TrendVO trendVO = commService.findById(trNo);

		trendVO.setTrNo(trNo);
		trendVO.setCateCd(cateCd);
		trendVO.setTrSubject(trSubject);
		trendVO.setTrContent(trContent);
		trendVO.setTrUpdate(new Date());

		trendService.saveTrend(trendVO);

		return "redirect:post?trNo=" + trNo;
	}

	@RequestMapping("deletePost")
	public String deletePost(HttpServletRequest request) {

		int trNo = Integer.parseInt(request.getParameter("trNo"));

		TrendVO trendVO = commService.findById(trNo);
		trendVO.setTrUpdate(new Date());
		trendVO.setTrDelYn('y');

		trendService.saveTrend(trendVO);

		return "redirect:/";
	}

}