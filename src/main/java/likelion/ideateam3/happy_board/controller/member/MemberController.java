package likelion.ideateam3.happy_board.controller.member;

import jakarta.validation.Valid;
import likelion.ideateam3.happy_board.domain.member.MemberPrincipal;
import likelion.ideateam3.happy_board.domain.member.dto.LoginRequest;
import likelion.ideateam3.happy_board.domain.member.dto.LoginResponse;
import likelion.ideateam3.happy_board.domain.member.dto.SignUpRequest;
import likelion.ideateam3.happy_board.response.ResponseBody;
import likelion.ideateam3.happy_board.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static likelion.ideateam3.happy_board.response.ResponseUtil.createSuccessResponse;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/sign-up")
    public ResponseEntity<ResponseBody<Void>> signUp(@RequestBody @Valid SignUpRequest request) {
        memberService.signUp(request);
        return ResponseEntity.ok(createSuccessResponse());
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseBody<LoginResponse>> login(@RequestBody @Valid LoginRequest request) {
        LoginResponse response = memberService.login(request);
        return ResponseEntity.ok(createSuccessResponse(response));
    }
}
