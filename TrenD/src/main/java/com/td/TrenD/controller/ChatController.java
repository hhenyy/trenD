package com.td.TrenD.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import com.td.TrenD.common.ChatRequest;
import com.td.TrenD.common.OpenAI;

import reactor.core.publisher.Flux;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

@Controller
public class ChatController {

    // @PostMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    //java에서는 sse(server side event)써서 event를 보냄
    @GetMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> chat(@RequestParam String prompt) {
        // String prompt = request.prompt;
        String model = "gpt-3.5-turbo-1106";
        System.out.println(prompt);
        List<String> messages = new ArrayList<String>();
        Flux<String> answer;
        if(prompt == null) {
            answer = Flux.just("Not","Worked");
        } else {
            answer = OpenAI.getInstance().getRespByStream(prompt, model);
        }
        Flux<ServerSentEvent<String>> result = answer.map(ans -> {
            messages.add(ans);
            ans = ans.replaceAll(" ", "+");
            ServerSentEvent<String> res = ServerSentEvent.builder(ans).build();
            return res;
        });

        // result.collectList().block().forEach(item -> {
        //     System.out.println(item.data());
        // });
        result.doOnComplete(() -> {
            String message = "";
            for(int i = 0; i < messages.size(); i++) {
                message += messages.get(i);
            }
            OpenAI.getInstance().addMessage("assistant", message);
        });

        return result;
    }
}
