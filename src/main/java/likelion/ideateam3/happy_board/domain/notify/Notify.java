package likelion.ideateam3.happy_board.domain.notify;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import likelion.ideateam3.happy_board.domain.common.BaseEntity;
import likelion.ideateam3.happy_board.domain.member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Notify extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notification_id")
	private Long id;

	private String content;

	private String url;

	@Column(nullable = false)
	private Boolean isRead;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private NotificationType notificationType;

	@ManyToOne
	@JoinColumn(name = "member_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Member receiver;

	@Builder
	public Notify(String content, String url, Boolean isRead, NotificationType notificationType, Member receiver) {
		this.content = content;
		this.url = url;
		this.isRead = isRead;
		this.notificationType = notificationType;
		this.receiver = receiver;
	}
}
