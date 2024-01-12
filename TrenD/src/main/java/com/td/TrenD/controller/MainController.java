package com.td.TrenD.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.shaded.json.parser.JSONParser;
import com.nimbusds.jose.shaded.json.parser.ParseException;
import com.td.TrenD.model.GTrend;
import com.td.TrenD.model.TotalSearchVO;
import com.td.TrenD.model.TrendVO;
import com.td.TrenD.service.TrendService;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class MainController {

    private final TrendService trendService;

    Dotenv dotenv = Dotenv.load();
    final String SERP_API_KEY = dotenv.get("SERP_API_KEY");

    @RequestMapping("/")
    public String main() {
        return "main/main";
    }

    //구글 일별 리스트
    @GetMapping("/googlelist")
    @ResponseBody
    public GTrend googlelist() {
         // 현재 날짜 구하기 (시스템 시계, 시스템 타임존)
        // LocalDate now = LocalDate.now();
        
        // // 현재 날짜 구하기(Paris)
        LocalDate now = LocalDate.now(ZoneId.of("Asia/Seoul"));

        // 포맷 정의
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        
        // 포맷 적용
        String formatedNow = now.format(formatter);

        HttpClient client = HttpClient.newHttpClient();

        String url = "https://serpapi.com/search?";
        List<String> queries = new ArrayList<String>();
        queries.add("engine=google_trends_trending_now");
        queries.add("frequency=daily");
        queries.add("geo=KR");
        queries.add("hl=ko");
        queries.add("date="+formatedNow);
        queries.add("api_key="+SERP_API_KEY);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url+String.join("&", queries)))
            .GET()
            .build();
        
        GTrend gtrend = new GTrend();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONParser jsonParser = new JSONParser();
            try {
                Object obj = jsonParser.parse(response.body());
                JSONObject jsonObj = (JSONObject) obj;
                ObjectMapper mapper = new ObjectMapper();
                gtrend = mapper.readValue(jsonObj.toJSONString(), GTrend.class);
                SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMdd");
                SimpleDateFormat newDtFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
                //람다식 사용법 : (매개변수, ...) -> { 실행문 ... }
                gtrend.daily_searches.forEach((daily_search) -> {
                    try {
                        Date formatDate = dtFormat.parse(daily_search.date);
                        String strNewDtFormat = newDtFormat.format(formatDate);
                        daily_search.date = strNewDtFormat;
                    } catch (java.text.ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    int prev_traffic = 99999999;
                    String[] suffix = {"천+", "만+", "십만+", "백만+"};
                    int suffix_idx = -1;
                    for(int i = daily_search.searches.size() - 1; i >= 0; i--) {
                        int traffic = daily_search.searches.get(i).traffic;
                        if(traffic < prev_traffic) {
                            suffix_idx++;
                        }
                        daily_search.searches.get(i).str_traffic = String.valueOf(traffic) + suffix[suffix_idx];
                        prev_traffic = traffic;
                    }
                });
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return gtrend;
    }

    @GetMapping("/search/comm")
    @ResponseBody
    public TotalSearchVO commTotalSearch(@RequestParam String keyword, @RequestParam(defaultValue = "1") String page) {

        String cateCd = "t";
        int count = trendService.commSearchResultCount("t", keyword, 'n');
        int size = 10;
        int lastPage = 1;
        if (count > 1) {
            lastPage = (count - 1) / size + 1;
        }
        int currentPage = Integer.parseInt(page) - 1;
        if (currentPage < 0) {currentPage = 0;}
        if (currentPage + 1 > lastPage) { currentPage = lastPage - 1;}
        int pageRange = 10;
        int startPage = currentPage - (currentPage % pageRange) + 1;
        int endPage = currentPage - currentPage % pageRange + pageRange;
        if (endPage > lastPage){endPage = lastPage;}

        Pageable pageable = PageRequest.of(currentPage, size);
        List<TrendVO> searchResult = trendService.commSearchResult(keyword, cateCd, pageable).getContent();

        TotalSearchVO result = new TotalSearchVO();
        result.setSearchResult(searchResult);
        result.setCurrentPage(Integer.toString(currentPage));
        result.setLastPage(Integer.toString(lastPage));
        result.setStartPage(Integer.toString(startPage));
        result.setEndPage(Integer.toString(endPage));
        result.setPageRange(Integer.toString(pageRange));

        return result;
    }

    @GetMapping("/search/trend")
    @ResponseBody
    public TotalSearchVO trendTotalSearch(@RequestParam String keyword, @RequestParam(defaultValue = "1") String page) {

        String cateCd = "t";
        int count = trendService.trendSearchResultCount("t", keyword, 'n');
        int size = 10;
        int lastPage = 1;
        if (count > 1) {
            lastPage = (count - 1) / size + 1;
        }
        int currentPage = Integer.parseInt(page) - 1;
        if (currentPage < 0) {currentPage = 0;}
        if (currentPage + 1 > lastPage) { currentPage = lastPage - 1;}
        int pageRange = 10;
        int startPage = currentPage - (currentPage % pageRange) + 1;
        int endPage = currentPage - currentPage % pageRange + pageRange;
        if (endPage > lastPage){endPage = lastPage;}

        Pageable pageable = PageRequest.of(currentPage, size);
        List<TrendVO> searchResult = trendService.trendSearchResult(keyword, cateCd, pageable).getContent();

        TotalSearchVO result = new TotalSearchVO();
        result.setSearchResult(searchResult);
        result.setCurrentPage(Integer.toString(currentPage));
        result.setLastPage(Integer.toString(lastPage));
        result.setStartPage(Integer.toString(startPage));
        result.setEndPage(Integer.toString(endPage));
        result.setPageRange(Integer.toString(pageRange));

        return result;
    }
}





