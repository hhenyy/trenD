package com.td.TrenD.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;  // Add this import
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.xml.sax.SAXException;  // Also, add this import

@Controller
@RequiredArgsConstructor
public class MainController {

    @RequestMapping("/")
    public String start() {
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
                String title = item.getElementsByTagName("title").item(0).getTextContent();
                System.out.println("Title: " + title);
            }

        } catch (IOException | InterruptedException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }

        System.out.println("MainController.start");
        return "main";
    }
}
