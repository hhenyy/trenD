package com.td.TrenD.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;

// https://json2csharp.com/code-converters/json-to-pojo 이거 사용해서 json -> java class로 변환
@JsonIgnoreProperties(ignoreUnknown = true) // json에 존재하는 key가 class에 member로 없어도 동작하도록 함
public class GTrend {
    // public SearchMetadata search_metadata;
    // public SearchParameters search_parameters;
    // public SerpapiPagination serpapi_pagination;
    public ArrayList<DailySearch> daily_searches;

    public static class Article{
        public String snippet;
        public String date;
        public String thumbnail;
        public String link;
        public String source;
        public String title;
        public String summary;
    }
    
    public static class DailySearch{
        public String date;
        public ArrayList<Search> searches;
    }
    
    public static class RelatedQuery{
        public String google_trends_link;
        public String query;
        public String serpapi_google_trends_link;
    }
    
    public static class Search{
        public String google_trends_link;
        public String query;
        public String serpapi_google_trends_link;
        public ArrayList<Article> articles;
        public int traffic;
        public String str_traffic;
        public String summary;
        public ArrayList<RelatedQuery> related_queries;
    }
    
    // public static class SearchMetadata{
    //     public String google_trends_trending_now_url;
    //     public String raw_html_file;
    //     public String created_at;
    //     public String processed_at;
    //     public String id;
    //     public String prettify_html_file;
    //     public double total_time_taken;
    //     public String json_endpoint;
    //     public String status;
    // }
    
    // public static class SearchParameters{
    //     public String date;
    //     public String geo;
    //     public String hl;
    //     public String engine;
    //     public String frequency;
    // }
    
    // public static class SerpapiPagination{
    //     public String next;
    //     public String current_date;
    //     public String next_date;
    // }
}
