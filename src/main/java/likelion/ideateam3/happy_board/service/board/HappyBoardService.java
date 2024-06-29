
package likelion.ideateam3.happy_board.service.board;

import static likelion.ideateam3.happy_board.response.exception.ExceptionType.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import likelion.ideateam3.happy_board.domain.board.HappyBoard;
import likelion.ideateam3.happy_board.domain.member.Member;
import likelion.ideateam3.happy_board.dto.board.BoardDto;
import likelion.ideateam3.happy_board.dto.board.BoardRequest;
import likelion.ideateam3.happy_board.repository.board.HappyBoardRepository;
import likelion.ideateam3.happy_board.repository.member.MemberRepository;
import likelion.ideateam3.happy_board.response.exception.BusinessException;
import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class HappyBoardService {

	private final HappyBoardRepository happyBoardRepository;
	private final MemberRepository memberRepository;

	@Transactional
	public void create(Long memberId, BoardRequest request) {
		Member savedMember = memberRepository.findById(memberId)
			.orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));

		HappyBoard happyBoard = request.toHappyBoard(savedMember);
		happyBoardRepository.save(happyBoard);
	}

	@Transactional
	public BoardDto update(Long boardId, BoardRequest request, Long memberId) {
		HappyBoard happyBoard = happyBoardRepository.findById(boardId)
			.orElseThrow(() -> new BusinessException(BOARD_NOT_FOUND_ERROR));

		if (!memberId.equals(happyBoard.getMember().getId()))
			throw new BusinessException(NOT_BOARD_OWNER);

		happyBoard.update(request);
		return new BoardDto(happyBoard);
	}

	public BoardDto read(Long boardId) {
		HappyBoard happyBoard = happyBoardRepository.findById(boardId)
			.orElseThrow(() -> new BusinessException(BOARD_NOT_FOUND_ERROR));
		return new BoardDto(happyBoard);
	}

	public List<BoardDto> readAll() {
		return happyBoardRepository.findAll()
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
}
