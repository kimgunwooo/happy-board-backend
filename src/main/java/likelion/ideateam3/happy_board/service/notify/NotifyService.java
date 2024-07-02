package likelion.ideateam3.happy_board.service.notify;

import static likelion.ideateam3.happy_board.response.exception.ExceptionType.*;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import likelion.ideateam3.happy_board.domain.member.Member;
import likelion.ideateam3.happy_board.domain.notify.NotificationType;
import likelion.ideateam3.happy_board.domain.notify.Notify;
import likelion.ideateam3.happy_board.dto.notify.NotifyDto;
import likelion.ideateam3.happy_board.repository.member.MemberRepository;
import likelion.ideateam3.happy_board.repository.notify.EmitterRepository;
import likelion.ideateam3.happy_board.repository.notify.NotifyRepository;
import likelion.ideateam3.happy_board.response.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class NotifyService {
	private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

	private final MemberRepository memberRepository;
	private final EmitterRepository emitterRepository;
	private final NotifyRepository notifyRepository;

	public SseEmitter subscribe(Long memberId, String lastEventId) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));

		String emitterId = makeTimeIncludeId(member.getEmail());
		SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));
		emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
		emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

		// 503 에러를 방지하기 위한 더미 이벤트 전송
		String eventId = makeTimeIncludeId(member.getEmail());
		sendNotification(emitter, eventId, emitterId, "EventStream Created. [userEmail=" + member.getEmail() + "]");

		// 클라이언트가 미수신한 Event 목록이 존재할 경우 전송하여 Event 유실을 예방
		if (hasLostData(lastEventId)) {
			log.info("event re-send");
			sendLostData(lastEventId, member.getEmail(), emitterId, emitter);
		}

		return emitter;
	}

	private String makeTimeIncludeId(String email) {
		return email + "_" + System.currentTimeMillis();
	}

	private void sendNotification(SseEmitter emitter, String eventId, String emitterId, Object data) {
		try {
			emitter.send(SseEmitter.event()
				.id(eventId)
				.name("sse")
				.data(data)
			);
		} catch (IOException exception) {
			emitterRepository.deleteById(emitterId);
		}
	}

	private boolean hasLostData(String lastEventId) {
		return !lastEventId.isEmpty();
	}

	private void sendLostData(String lastEventId, String email, String emitterId, SseEmitter emitter) {
		Map<String, Object> eventCaches = emitterRepository.findAllEventCacheStartWithByMemberId(email);
		log.info("eventCaches size : {}", eventCaches.size());
		eventCaches.entrySet().stream()
			.filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
			.forEach(entry -> {
				log.info("key : {}, value : {}", entry.getKey(), entry.getValue());
				sendNotification(emitter, entry.getKey(), emitterId, entry.getValue());
			});
	}

	@Transactional
	public void send(Member receiver, NotificationType notificationType, String content, String url) {
		Notify notification = notifyRepository.save(createNotification(receiver, notificationType, content, url));

		String receiverEmail = receiver.getEmail();
		String eventId = receiverEmail + "_" + System.currentTimeMillis();
		Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByMemberId(receiverEmail);
		emitters.forEach(
			(key, emitter) -> {
				emitterRepository.saveEventCache(key, notification);
				sendNotification(emitter, eventId, key, NotifyDto.createResponse(notification));
			}
		);
	}

	private Notify createNotification(Member receiver, NotificationType notificationType, String content, String url) {
		return Notify.builder()
			.receiver(receiver)
			.notificationType(notificationType)
			.content(content)
			.url(url)
			.isRead(false)
			.build();
	}
}
