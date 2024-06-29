package likelion.ideateam3.happy_board.dto.board;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import likelion.ideateam3.happy_board.domain.board.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class BoardDto {
	private Long id;
	private String title;
	private String content;
	private MemberInfo member;
	private int comments;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime createdAt;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime modifiedAt;

	@Getter
	@AllArgsConstructor
	static class MemberInfo {
		private Long id;
		private String nickname;
	}

	public BoardDto(Board board) {
		this.id = board.getId();
		this.title = board.getTitle();
		this.content = board.getContent();
		this.member = new MemberInfo(board.getMember().getId(), board.getMember().getNickname());
		this.comments = board.getComments().size();
		this.createdAt = board.getCreatedAt();
		this.modifiedAt = board.getModifiedAt();
	}
}
