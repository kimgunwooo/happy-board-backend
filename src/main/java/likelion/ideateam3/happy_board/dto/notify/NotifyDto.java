package likelion.ideateam3.happy_board.dto.notify;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import likelion.ideateam3.happy_board.domain.notify.NotificationType;
import likelion.ideateam3.happy_board.domain.notify.Notify;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NotifyDto {
	String id;
	String nickname;
	String content;
	String url;
	Boolean isRead;
	NotificationType type;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	LocalDateTime createdAt;

	public static NotifyDto createResponse(Notify notify) {
		return NotifyDto.builder()
			.id(notify.getId().toString())
			.nickname(notify.getReceiver().getNickname())
			.content(notify.getContent())
			.url(notify.getUrl())
			.isRead(notify.getIsRead())
			.type(notify.getNotificationType())
			.createdAt(notify.getCreatedAt())
			.build();

	}
}
