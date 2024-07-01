package likelion.ideateam3.happy_board.dto.notify;

import likelion.ideateam3.happy_board.domain.member.Member;
import likelion.ideateam3.happy_board.domain.notify.NotificationType;

public interface NotifyInfo {
	Member getReceiver();
	Long getGoUrlId();
	NotificationType getNotificationType();
}
