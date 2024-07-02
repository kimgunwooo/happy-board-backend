package likelion.ideateam3.happy_board.controller.notify;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import likelion.ideateam3.happy_board.domain.member.MemberPrincipal;
import likelion.ideateam3.happy_board.service.notify.NotifyService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notify")
public class NotifyController {

	private final NotifyService notifyService;

	@GetMapping(value = "/subscribe", produces = "text/event-stream")
	public SseEmitter subscribe(@AuthenticationPrincipal MemberPrincipal member,
		@RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {

		return notifyService.subscribe(member.getMemberId(), lastEventId);
	}
}
