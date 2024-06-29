package likelion.ideateam3.happy_board.controller.board;

import static likelion.ideateam3.happy_board.response.ResponseUtil.*;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import likelion.ideateam3.happy_board.domain.member.MemberPrincipal;
import likelion.ideateam3.happy_board.dto.board.BoardDto;
import likelion.ideateam3.happy_board.dto.board.BoardRequest;
import likelion.ideateam3.happy_board.response.ResponseBody;
import likelion.ideateam3.happy_board.service.board.HappyBoardService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards/happy")
public class HappyBoardController {

	private final HappyBoardService happyBoardService;

	@PostMapping
	public ResponseEntity<ResponseBody<Void>> create(@Valid @RequestBody BoardRequest request,
		@AuthenticationPrincipal MemberPrincipal member) {
		happyBoardService.create(member.getMemberId(), request);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(createSuccessResponse());
	}

	@PatchMapping("/{boardId}")
	public ResponseEntity<ResponseBody<BoardDto>> update(@PathVariable Long boardId,
		@Valid @RequestBody BoardRequest request, @AuthenticationPrincipal MemberPrincipal member) {
		BoardDto boardDto = happyBoardService.update(boardId, request, member.getMemberId());
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(createSuccessResponse(boardDto));
	}

	@GetMapping("/{boardId}")
	public ResponseEntity<ResponseBody<BoardDto>> read(@PathVariable Long boardId) {
		BoardDto boardDto = happyBoardService.read(boardId);
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(createSuccessResponse(boardDto));
	}

	@GetMapping
	public ResponseEntity<ResponseBody<List<BoardDto>>> readAll() {
		List<BoardDto> boardDtoList = happyBoardService.readAll();
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(createSuccessResponse(boardDtoList));
	}

	@DeleteMapping("/{boardId}")
	public ResponseEntity<ResponseBody<Void>> delete(@PathVariable Long boardId,
		@AuthenticationPrincipal MemberPrincipal member) {
		happyBoardService.delete(boardId, member.getMemberId());
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(createSuccessResponse());
	}
}
