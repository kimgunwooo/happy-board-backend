package likelion.ideateam3.happy_board.event;

import likelion.ideateam3.happy_board.domain.member.Member;

public record DegreeOfHazardTestingEvent(
	Long boardId,
	String message,
	Member member
) {
}
