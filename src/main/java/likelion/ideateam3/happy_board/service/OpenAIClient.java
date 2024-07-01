package likelion.ideateam3.happy_board.service;

import likelion.ideateam3.happy_board.dto.WebClientRequest;
import likelion.ideateam3.happy_board.dto.WebClientResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class OpenAIClient {

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiURL;

    @Value("${openai.api.maxTokens}")
    private int maxTokens;

    private final WebClient webClient;

    // request 보내고 resposne 받아옴
    public Mono<WebClientResponse> doRequest(String message) {
        WebClientRequest webClientRequest = new WebClientRequest(model,message,maxTokens); // model 과 채팅문(?)과 최대토큰 설정
        log.info("request 내용 : " +webClientRequest.toString());

        Mono<WebClientResponse> webClientResponseMono = webClient.post() // post 방식 요청
                .bodyValue(webClientRequest) // request body 설정
                .retrieve() //response body를 받아 디코딩하는 간단한 메소드
                .bodyToMono(WebClientResponse.class); // 가져온 body를 Mono 객체로 바꿔줌

        webClientResponseMono.subscribe(result -> log.info("response 내용: "+result.toString()));

        return webClientResponseMono;
    }

    // request 보내고 받아온 resposne 에서 유해성 판단 결과 뽑아서 반환
    public Mono<Integer> analyzeBoardHazard(String message) {
        return doRequest(message)
            .map(response -> Integer.valueOf(response.getChoices().get(0).getMessage().getContent()));
    }
}
