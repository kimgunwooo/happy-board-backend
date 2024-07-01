package likelion.ideateam3.happy_board.dto.board;

import jakarta.validation.constraints.NotBlank;
import likelion.ideateam3.happy_board.domain.board.AnnouncementBoard;
import likelion.ideateam3.happy_board.domain.board.HappyBoard;
import likelion.ideateam3.happy_board.domain.board.ShareBoard;
import likelion.ideateam3.happy_board.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BoardRequest {
	@NotBlank(message = "제목은 빈값일 수 없습니다.")
	private String title;
	@NotBlank(message = "내용은 빈값일 수 없습니다.")
	private String content;

	public HappyBoard toHappyBoard(Member member) {
		return HappyBoard.builder()
			.title(title)
			.content(content)
			.member(member)
			.build();
	}

	public ShareBoard toShareBoard(Member member) {
		return ShareBoard.builder()
			.title(title)
			.content(content)
			.member(member)
			.build();
	}

	public AnnouncementBoard toAnnouncementBoard(Member member) {
		return AnnouncementBoard.builder()
			.title(title)
			.content(content)
			.member(member)
			.build();
	}
}
