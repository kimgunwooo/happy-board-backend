package likelion.ideateam3.happy_board.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@ToString
@Getter
public class WebClientRequest {
    private String model;
    private List<Message> messages;
    private int max_tokens; // 혹시 몰라서 토큰 수를 최대 1로 제한


    public WebClientRequest(String model, String message, int maxTokens){
        this.model=model;

        Message sysMessage= new Message("system", "당신은 게시글의 유해성 정도를 0~10 의 숫자로 평가하는 전문가입니다. 이유는 설명하지 말고 숫자로만 답변해주세요.");
        Message userMessage= new Message("user", message);

        this.messages =  new ArrayList<>();
        this.messages.add(sysMessage);
        this.messages.add(userMessage);
        this.max_tokens=maxTokens;
    }

}
