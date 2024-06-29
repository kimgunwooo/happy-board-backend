package likelion.ideateam3.happy_board.domain.board;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import likelion.ideateam3.happy_board.domain.member.Member;
import likelion.ideateam3.happy_board.dto.board.BoardRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DiscriminatorValue("happy")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HappyBoard extends Board{

	@Builder
	public HappyBoard(String title, String content, Member member) {
		super(title, content, member);
	}

	public void update(BoardRequest request) {
		this.title = request.getTitle();
		this.content = request.getContent();
	}
}
