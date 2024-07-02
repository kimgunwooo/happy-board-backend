
package likelion.ideateam3.happy_board.service.board;

import static likelion.ideateam3.happy_board.response.exception.ExceptionType.*;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import likelion.ideateam3.happy_board.domain.board.Board;
import likelion.ideateam3.happy_board.domain.board.HappyBoard;
import likelion.ideateam3.happy_board.domain.member.Member;
import likelion.ideateam3.happy_board.dto.board.BoardDto;
import likelion.ideateam3.happy_board.dto.board.BoardRequest;
import likelion.ideateam3.happy_board.event.DegreeOfHazardTestingEvent;
import likelion.ideateam3.happy_board.repository.board.HappyBoardRepository;
import likelion.ideateam3.happy_board.repository.member.MemberRepository;
import likelion.ideateam3.happy_board.response.exception.BusinessException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class HappyBoardService {

	private final HappyBoardRepository happyBoardRepository;
	private final MemberRepository memberRepository;
	private final ApplicationEventPublisher eventPublisher;

	@Transactional
	public void create(Long memberId, BoardRequest request) {
		Member savedMember = memberRepository.findById(memberId)
			.orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));

		HappyBoard happyBoard = request.toHappyBoard(savedMember);
		HappyBoard savedHappyBoard = happyBoardRepository.save(happyBoard);

		this.publishDegreeOfHazardTestingEvent(savedHappyBoard);
	}

	@Transactional
	public BoardDto update(Long boardId, BoardRequest request, Long memberId) {
		HappyBoard happyBoard = happyBoardRepository.findById(boardId)
			.orElseThrow(() -> new BusinessException(BOARD_NOT_FOUND_ERROR));

		if (!memberId.equals(happyBoard.getMember().getId()))
			throw new BusinessException(NOT_BOARD_OWNER);

		happyBoard.update(request);

		this.publishDegreeOfHazardTestingEvent(happyBoard);

		return new BoardDto(happyBoard);
	}

	private void publishDegreeOfHazardTestingEvent(HappyBoard happyBoard) {
		DegreeOfHazardTestingEvent event = new DegreeOfHazardTestingEvent(
			happyBoard.getId(),
			happyBoard.getContent(),
			happyBoard.getMember());
		eventPublisher.publishEvent(event);
	}

	@Transactional(readOnly = true)
	public BoardDto read(Long boardId) {
		HappyBoard happyBoard = happyBoardRepository.findById(boardId)
			.orElseThrow(() -> new BusinessException(BOARD_NOT_FOUND_ERROR));
		return new BoardDto(happyBoard);
	}

	@Transactional(readOnly = true)
	public List<BoardDto> readAllHazardFalse() {
		return happyBoardRepository.findByHazardFalse()
			.stream()
			.map(BoardDto::new)
			.toList();
	}

	@Transactional
	public void delete(Long boardId, Long memberId) {
		HappyBoard happyBoard = happyBoardRepository.findById(boardId)
			.orElseThrow(() -> new BusinessException(BOARD_NOT_FOUND_ERROR));

		if (!memberId.equals(happyBoard.getMember().getId()))
			throw new BusinessException(NOT_BOARD_OWNER);

		happyBoardRepository.delete(happyBoard);
	}

	@Transactional(readOnly = true)
	public List<BoardDto> readAllMeHazardFalse(Long memberId) {
		return happyBoardRepository.findByHazardFalseAndMemberId(memberId)
			.stream()
			.map(BoardDto::new)
			.toList();
	}

	@Transactional(readOnly = true)
	public List<BoardDto> readAllMeHazardTrue(Long memberId) {
		return happyBoardRepository.findByHazardTrueAndMemberId(memberId)
			.stream()
			.map(BoardDto::new)
			.toList();
	}


	public void updateBoardHazardStatus(Long boardId, boolean hazardStatus) {
		HappyBoard happyBoard = happyBoardRepository.findById(boardId)
			.orElseThrow(() -> new BusinessException(BOARD_NOT_FOUND_ERROR));
		happyBoard.setHazard(hazardStatus);
		happyBoardRepository.save(happyBoard);
	}


}
