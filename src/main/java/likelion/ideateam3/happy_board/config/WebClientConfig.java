package likelion.ideateam3.happy_board.config;

import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@ToString
@Configuration
public class WebClientConfig {

    @Value("${openai.api.key}")
    private String openAiKey;

    @Value("${openai.api.url}")
    private String apiURL;

    @Bean
    public WebClient webclient(){
        WebClient client = WebClient.builder()
                // 호출할 URL
                .baseUrl(apiURL)
                // 모든 요청에 사용할 헤더
                .defaultHeaders( httpHeaders ->{
                        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                        httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + openAiKey);}
                )
                .build();

        return client;
    }





}
