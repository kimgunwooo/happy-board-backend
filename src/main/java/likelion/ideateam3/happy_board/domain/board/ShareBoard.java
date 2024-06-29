package likelion.ideateam3.happy_board.domain.board;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import likelion.ideateam3.happy_board.domain.member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DiscriminatorValue("share")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShareBoard extends Board{

	@Builder
	public ShareBoard(String title, String content, Member member) {
		super(title, content, member);
	}
}
