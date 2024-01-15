package com.td.TrenD.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.td.TrenD.service.AgeService;
import com.td.TrenD.service.CategoryService;
import com.td.TrenD.service.GenderService;
import com.td.TrenD.service.LocationService;
import com.td.TrenD.service.StatisticsService;
import com.td.TrenD.service.TrendService;

@Controller
public class StatisticsDetailController {
	
	@Autowired
	StatisticsService service;
	@Autowired
	TrendService trendService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	AgeService ageService;
	@Autowired
	GenderService genderService;
	@Autowired
	LocationService locationService;

	//상세 통계 : 성별,연령,지역에 따른 통계를 표시. 어떤 성별/지역/연령을 가진 사람들이 해당 키워드에 접속했을까?
		//아 이거 애매하네....어떤 식으로 통계를 출력해야 할지? 성별이야 상관없지만 지역과 연령의 경우 없는 데이터가 존재할 가능성이 큼
		//특히 지역의 경우 20개 정도의 권역이 설정되어 있기에 대부분의 경우 반 이상은 비어있을 것
		//걱정하지 않아도 됨. 0이 전송되면 뷰페이지의 통계 라이브러리가 알아서 처리해줌
		//뷰페이지에선 버튼을 통해서 연령,성별,지역 등으로 값이 변경될 예정
	
		@RequestMapping("/StatisticsDetail/{value}")//포스트 방식의 요청을 받는 어노테이션..이어야 하지만 RequestMapping로도 작동
													//겟메핑, 포스트매핑 등은 RequestMapping를 세분화한 것. 작동하면 이거 써도 됨
					 //링크를 통해 전송된 값들은 /{}을 통해 받아야 한다
		@ResponseBody
		public ResponseEntity<Map<String, Object>> StatisticsDetail(/* @PathVariable("category") String category, */
									   								@PathVariable("value") String value,
									   								@RequestParam("category") String category) {
																	//링크를 통해 전송된 값들을 받기 위한 @PathVariable 어노테이션
																	//링크가 아닌 json을 통해 전송된 값은 @RequestParam에 키값을 입력해서 받는다
			
			List<String> vlist = new ArrayList<String>();   //10대,20대,30대,... 등 해당 분류의 모든 값을 저장
			List<String> clist = new ArrayList<String>();	//10대,40대,50대,20대,20대,... 등 통계 테이블에 저장된 해당 분류의 모든 값을 저장
			
			List<Object> list = new ArrayList<Object>();	//[분류,클릭횟수]를 담을 리스트
			List<Object> finalList = new ArrayList<Object>();	//위의 리스트를 담을 리스트
			
			//세 개의 sql문을 불러올 것. value값에 따라 다른 sql문
			//연령,성별,지역 세 개의 값이 select를 통해 전달되어 옴. 이를 받아 활용
			//1차적으로 cateNm값. 이 값은 워드클라우드의 단어를 클릭 시 자동으로 해당 단어가 등록됨
			//2차적으로 select의 값. 기본적으론 성별로 설정. 이후 select 내용이 바뀔 때마다 이 컨트롤러가 호출됨
			
			if(value.isEmpty()) {
				value = "연령";
			}
			System.out.println(value);
			System.out.println(category);
			
			if(value.equals("연령")) {
				vlist = ageService.getList();
				clist = ageService.countAge(category);
			}else if(value.equals("성별")) {
				vlist = genderService.getList();
				clist = genderService.countGender(category);
			}else if(value.equals("지역")) {
				vlist = locationService.getList();
				clist = locationService.countLocation(category);
			}else {
				//return "StatisticsDetail";
			}
			System.out.println(clist);
			
			for(String v : vlist) {
				int count = Collections.frequency(clist, v);
				if(count != 0) {
				//list.add("'"+v+"'");  //v값을 이런 형태로 전송하던 건 그래프 라이브러리가 '문자열' 형태로 값을 받아 구현하기 때문
				list.add(v);			//현재는 list를 map에 따로 담아 가기 때문에 이런 처리가 필요 없다
				list.add(count);   //list 안에는 v와 count 값이 들어가있음
				System.out.println(list);
				//1. toArray 메소드는 List를 파라메터(매개변수)로 넘어가는 배열 객체의 size만큼의 배열로 전환한다.
				//2. 단, 해당 List size가 인자로 넘어가는 배열 객체의 size보다 클때, 해당 List의 size로 배열이 만들어진다.
				//3. 반대로 해당 List size가 인자로 넘어가는 배열객체의 size보다 작을때는, 인자로 넘어가는 배열객체의 size로 배열이 만들어진다.
				finalList.add(list);
				list = new ArrayList<Object>();	//값을 초기화하여 list를 깨끗이 비운다. 이 작업이 없다면 값이 전부 쌓인 list가 만들어짐
				//System.out.println(Arrays.toString(newList));
				//newList = new Object[2];
				//System.out.print(v);
				//System.out.println(count);
				}
			}
			System.out.println(finalList);
			//clist 내부에 있는 vlist 내부의 값들 갯수를 센 후, 연관지어 전송
			
//			model.addAttribute("list", finalList);
//			model.addAttribute("value", value);
//			model.addAttribute("category", category);
			Map map = new HashMap<>();
			map.put("list", finalList);
			map.put("value", value);
			map.put("category", category);	//여러 값을 전송하게 되면 map 객체에 담아 전송해줘야 한다
			
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
}
