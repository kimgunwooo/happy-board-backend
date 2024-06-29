package likelion.ideateam3.happy_board.controller;

import likelion.ideateam3.happy_board.service.OpenAIClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/ai2")
public class AiTestApiController {
    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiURL;

    private int maxTokens=1;


    private final OpenAIClient openAIClient;


   @GetMapping("/webclient")
    public String doTest(@RequestBody String message) {
       openAIClient.analyzeBoardHazard(message).subscribe(result -> log.info("여기 result 를 활용하는 코드 작성"));

       return "완료";
    }



}
