package likelion.ideateam3.happy_board.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import likelion.ideateam3.happy_board.domain.member.Member;
import lombok.Getter;

@Getter
public class SignUpRequest {
    @NotBlank(message = "사용자 이메일은 빈 값일 수 없습니다.")
    private String email;

    @NotBlank(message = "비밀번호는 빈 값일 수 없습니다.")
    private String password;

    @NotBlank(message = "name은 빈 값일 수 없습니다.")
    private String name;

    @NotBlank(message = "nickname은 빈 값일 수 없습니다.")
    private String nickname;

    public static Member toEntity(String email, String newPassword, String name, String nickname) {
        return Member.builder()
                .email(email)
                .password(newPassword)
                .name(name)
                .nickname(nickname)
                .build();
    }
}
