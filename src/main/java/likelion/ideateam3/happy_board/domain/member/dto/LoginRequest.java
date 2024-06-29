package likelion.ideateam3.happy_board.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import likelion.ideateam3.happy_board.domain.member.Member;
import lombok.Getter;

@Getter
public class LoginRequest {
    @NotBlank(message = "사용자 이메일은 빈 값일 수 없습니다.")
    private String email;

    @NotBlank(message = "비밀번호는 빈 값일 수 없습니다.")
    private String password;
}
