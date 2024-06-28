package likelion.ideateam3.happy_board.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
// 클래스 계층 구조를 직렬화/역직렬화할 때 어떤 속성을 기준으로 타입 정보를 포함할지를 정의
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME, //  클래스 이름을 사용하여 타입 정보를 나타냄
        include = JsonTypeInfo.As.PROPERTY, // 타입 정보를 JSON의 속성으로 포함시킴
        property = "success") // 타입 정보를 포함할 속성의 이름을 "success"로 지정
// 서브 타입 정보를 정의
@JsonSubTypes({
        @JsonSubTypes.Type(value = SuccessResponseBody.class, name = "true"), // "true"라는 이름으로 SuccessResponseBody 클래스를 서브 타입으로 지정
        @JsonSubTypes.Type(value = FailedResponseBody.class, name = "false")
})
@JsonIgnoreProperties(ignoreUnknown = true) // JSON 직렬화/역직렬화 과정에서 알 수 없는 속성을 무시
@JsonInclude(JsonInclude.Include.NON_NULL) //  null이 아닌 속성만 JSON에 포함
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public sealed abstract class ResponseBody<T> permits SuccessResponseBody, FailedResponseBody {
    // sealed >> 특정 서브 클래스들만 상속할 수 있도록 합
    private String code;
}
