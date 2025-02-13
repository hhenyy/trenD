package com.td.TrenD.common;

import java.util.ArrayList;
import java.util.List;

import com.theokanning.openai.completion.chat.*;
import com.theokanning.openai.embedding.EmbeddingRequest;
import com.theokanning.openai.service.OpenAiService;

import io.github.cdimascio.dotenv.Dotenv;
import io.reactivex.Flowable;
import reactor.core.publisher.Flux;

public class OpenAI {
	//OpenAI instance 생성
	private static OpenAI instance = null;
	//openAI API key
	Dotenv dotenv = Dotenv.load();
	private String API_KEY = dotenv.get("OPENAI_API_KEY");

	private OpenAiService service;
	private int maxToken = 512;
	private List<ChatMessage> messages;

	public static OpenAI getInstance() {
		if(instance == null) {
			instance = new OpenAI();
		}

		return instance;
	}

	private OpenAI() {
		if(API_KEY == null || API_KEY.equals("")) {
			service = null;
		} else {
			service = new OpenAiService(API_KEY);
			messages = new ArrayList<ChatMessage>();
		}
	}
	
	public OpenAiService getService() {
		return service;
	}
	
	public String getRespByTxt(String prompt, String model) {
		System.out.println("user ask:"+prompt);
		ChatMessage msg = new ChatMessage("user", prompt);
		messages = new ArrayList<ChatMessage>();
		messages.add(msg);

		ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
				.messages(messages)
		        .model(model)
		        .maxTokens(maxToken)
		        .n(1)
		        .topP(1.0)
		        .stream(false)
		        .build();

		String answer = service.createChatCompletion(chatCompletionRequest).getChoices().get(0).getMessage().getContent();		
		System.out.println("AI ans:"+answer);
		
		return answer;
	}

	public Flux<String> getRespByStream(String prompt, String model) {
		System.out.println("user ask:"+prompt);
		ChatMessage msg = new ChatMessage("user", prompt);
		messages.add(msg);

		ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
				.messages(messages)
		        .model(model)
		        .maxTokens(maxToken)
		        .n(1)
		        .topP(1.0)
		        .stream(true)
		        .build();
		Flowable<ChatCompletionChunk> flowable = service.streamChatCompletion(chatCompletionRequest);
		Flux<String> resultFlux = Flux.from(flowable).map(
			chunk -> {
				String content = chunk.getChoices().get(0).getMessage().getContent();
				if(content == null) {
					content = "";
				}

				return content;
			}
		);

		return resultFlux;
	}
	
	//embeddingRequest로 text를 숫자로 바꿈 
	public List<Double> getEmbedding(String text) {
		List<Double> res;

		List<String> in = new ArrayList<String>();
		in.add(text);
		String model = "text-embedding-ada-002";
		EmbeddingRequest embeddingRequest = EmbeddingRequest.builder()
				.input(in)
				.model(model)
				.build();
		
		res = service.createEmbeddings(embeddingRequest).getData().get(0).getEmbedding();
		
		return res;
	}

	public void addMessage(String role, String message) {
		ChatMessage msg = new ChatMessage(role, message);
		messages.add(msg);
	}
}