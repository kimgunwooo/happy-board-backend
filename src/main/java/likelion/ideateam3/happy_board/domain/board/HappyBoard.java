package likelion.ideateam3.happy_board.domain.board;

import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import likelion.ideateam3.happy_board.domain.member.Member;
import likelion.ideateam3.happy_board.dto.board.BoardRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@DiscriminatorValue("happy")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HappyBoard extends Board{

	@Setter
	@Column(nullable = false)
	private boolean hazard;

	@Builder
	public HappyBoard(String title, String content, Member member) {
		super(title, content, member);
		this.hazard = false;
	}

	public void update(BoardRequest request) {
		this.title = request.getTitle();
		this.content = request.getContent();
	}
}
