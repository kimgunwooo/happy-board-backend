package likelion.ideateam3.happy_board.event;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import likelion.ideateam3.happy_board.domain.notify.NotificationType;
import likelion.ideateam3.happy_board.domain.notify.NotifyMessage;
import likelion.ideateam3.happy_board.service.OpenAIClient;
import likelion.ideateam3.happy_board.service.board.HappyBoardService;
import likelion.ideateam3.happy_board.service.notify.NotifyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.scheduler.Schedulers;

@Slf4j
@RequiredArgsConstructor
@Component
public class DegreeOfHazardTestingEventHandler {

	private final OpenAIClient openAIClient;
	private final HappyBoardService happyBoardService;
	private final NotifyService notifyService;

	@EventListener
	@Transactional
	public void handleHappyBoardCreatedEvent(DegreeOfHazardTestingEvent event) {
		log.info("DegreeOfHazardTestingEvent consume : {}", event.message());

		openAIClient.analyzeBoardHazard(event.message())
			.publishOn(Schedulers.boundedElastic())
			.doOnNext(result -> {
				log.info("Degree of Hazard: {}", result);

				if(result >= 5) { // 일단은 5이상이라면 비정상적인 글로 판단
					happyBoardService.updateBoardHazardStatus(event.boardId(), true);
					notifyService.send(
						event.member(),
						NotificationType.WARN,
						NotifyMessage.HAZARD.getMessage(),
						"/api/boards/happy/" + event.boardId());
				} else {
					happyBoardService.updateBoardHazardStatus(event.boardId(), false);
					notifyService.send(
						event.member(),
						NotificationType.SUCCESS,
						NotifyMessage.SUCCESS.getMessage(),
						"/api/boards/happy/" + event.boardId());
				}
			})
			.subscribe();
	}
}
