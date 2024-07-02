package likelion.ideateam3.happy_board.domain.notify;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum NotifyMessage {
	HAZARD("해당 글이 유해성 검사에 의해 필터링되었습니다."),
	SUCCESS("해당 글이 유해성 검사를 통과한 후, 정상적으로 업로드 되었습니다."),
	;
	private String message;
}
