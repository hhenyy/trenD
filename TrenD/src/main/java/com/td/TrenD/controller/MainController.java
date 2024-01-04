package com.td.TrenD.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.shaded.json.parser.JSONParser;
import com.nimbusds.jose.shaded.json.parser.ParseException;
import com.td.TrenD.model.GTrend;
import com.td.TrenD.model.TotalSearchVO;
import com.td.TrenD.model.TrendVO;
import com.td.TrenD.service.TrendService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// import com.github.serpapi.GoogleSearch;
// import serpapi.GoogleSearch;


@Controller
@RequiredArgsConstructor
public class MainController {

    private final TrendService trendService;

    final String SERP_API_KEY = "";

//    System.out.println("controller in");
//            System.out.println("controller in");
    //    @RequestMapping("/googlelist")
//    public String googlelist(Model model) {

    //  @ResponseBody
    // public String googlelist() {

    @RequestMapping("/")
    public String main() {

        return "main/main";
    }

    //구글 일별 리스트
/*     @GetMapping("/googlelist")
     @ResponseBody
    public List<String> googlelist() {
        System.out.println("googlelist");
        String title = "";
        List list = new ArrayList();
        try {
            // XML 데이터를 가져오기 위해 HttpClient 사용
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://trends.google.com/trends/trendingsearches/daily/rss?geo=KR"))
                    .build();
            HttpResponse<InputStream> response = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());

            // XML 데이터 파싱
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(response.body());

            // 각 항목의 title을 가져와서 출력
            NodeList itemList = document.getElementsByTagName("item");
            for (int i = 0; i < itemList.getLength(); i++) {
                Element item = (Element) itemList.item(i);
                title = item.getElementsByTagName("title").item(0).getTextContent();
                System.out.println("Title: " + title);

                list.add(title);
            }
           System.out.println(list);
        } catch (IOException | InterruptedException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
      //  return title;
       return list;
    }*/

    //구글 맞춤 리스트
    @GetMapping("/mylist")
    @ResponseBody
    public GTrend mylist() {
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
                // JSONArray dailySearches = (JSONArray)jsonObj.get("daily_searches");
                gtrend = mapper.readValue(jsonObj.toJSONString(), GTrend.class);
                gtrend.daily_searches.forEach((daily_search) -> {
                    daily_search.searches.forEach((search) -> {
                        if(search.articles.size() > 0 ) {
                            String articleLink = search.articles.get(0).link;
                            try {
                                org.jsoup.nodes.Document document = Jsoup.connect(articleLink).get();
                                // String html = document.html();
                                String text = document.text();
                                //System.out.println(articleLink + "\n" + text);

                                // HttpRequest article_request = HttpRequest.newBuilder()
                                //     .uri(URI.create("https://api.arachn.io/booh1cxg5suxjets/domains/parse"))
                                //     .POST(BodyPublishers.ofString(String.format("{ \"hostname\": \"{0}\" }", articleLink)))
                                //     .setHeader("Content-Type", "application/json")
                                //     .setHeader("X-BLOBR-KEY", "eCMnanbmvQFbO75D5wSk6mEFkl5Flupz")
                                //     .build();

                                // HttpResponse<String> article_response = client.send(article_request, HttpResponse.BodyHandlers.ofString());
                            } catch (Exception e) {

                            }
                        }
                    });
                });
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return gtrend;
        // Map<String, String> parameter = new HashMap<String, String>();

        // parameter.put("engine", "google_trends_trending_now");
        // parameter.put("frequency", "daily");
        // parameter.put("date", "20230722");
        // parameter.put("api_key", "a0060db95d63b845dc84d8a222d3bdc5366e6ac3421ae0cbf6e3fbc325dbf1b2");

        // GoogleSearch search = new GoogleSearch(parameter);

        // try
        // {
        // JsonObject results = search.getJson();
        // var daily_searches = results.get("daily_searches");
        // }
        // catch (SerpApiClientException ex)
        // {
        // Console.WriteLine("Exception:");
        // Console.WriteLine(ex.ToString());
        // }
        // System.out.println("mylist");
        // String title = "";
        // List list = new ArrayList();
        // try {
        //     // XML 데이터를 가져오기 위해 HttpClient 사용
        //     HttpClient httpClient = HttpClient.newHttpClient();
        //     HttpRequest request = HttpRequest.newBuilder()
        //             .uri(URI.create("https://trends.google.com/trends/trendingsearches/daily/rss?geo=KR"))
        //             .build();
        //     HttpResponse<InputStream> response = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());

        //     // XML 데이터 파싱
        //     DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //     DocumentBuilder builder = factory.newDocumentBuilder();
        //     Document document = builder.parse(response.body());

        //     // 각 항목의 title을 가져와서 출력
        //     NodeList itemList = document.getElementsByTagName("item");
        //     for (int i = 0; i < itemList.getLength(); i++) {
        //         Element item = (Element) itemList.item(i);
        //         title = item.getElementsByTagName("title").item(0).getTextContent();
        //         System.out.println("Title: " + title);
        //         //model.addAttribute("title", title);
        //         //return "/main/main";

        //         list.add(title);
        //     }
        //     System.out.println(list);
        // } catch (IOException | InterruptedException | ParserConfigurationException | SAXException e) {
        //     e.printStackTrace();
        // }
        // return list;
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





