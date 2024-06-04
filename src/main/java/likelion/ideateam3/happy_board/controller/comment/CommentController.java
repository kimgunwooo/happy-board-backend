package likelion.ideateam3.happy_board.controller.comment;



import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import likelion.ideateam3.happy_board.dto.CommentDTO;
import likelion.ideateam3.happy_board.response.ResponseBody;
import likelion.ideateam3.happy_board.response.ResponseUtil;
import likelion.ideateam3.happy_board.response.exception.BusinessException;
import likelion.ideateam3.happy_board.response.exception.ExceptionType;
import likelion.ideateam3.happy_board.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@Slf4j
@RequestMapping("api/board/comment")
@RestController
@RequiredArgsConstructor
@Tag(name="comment 관리 API")
public class CommentController {
    private final CommentService commentService;

    @DeleteMapping("/comments/{boardId}")
    @Operation(
            summary = "특정 게시물에 달린 모든 댓글을 삭제하는 API",
            description = "pathvariable 로 받은 게시물의 id를 기준으로 모든 댓글이 삭제됩니다"
    )
    public ResponseEntity<ResponseBody<String>>  deleteAllComment(@PathVariable Long boardId){
             commentService.deleteByBoardId(boardId);
        return  ResponseEntity.status(HttpStatus.OK) .body(ResponseUtil.createSuccessResponse("정상적으로 삭제됨"));
    }

    @GetMapping("/comments/{boardId}")
    @Operation(
            summary = "특정 게시물에 달린 모든 댓글을 조회하는 API",
            description = "pathvariable 로 받은 게시물의 id를 기준으로 모든 댓글이 조회됩니다"
    )
    public ResponseEntity<ResponseBody<List<CommentDTO>>> getCommentsById(@PathVariable Long boardId ){
            List<CommentDTO>  response = commentService.getCommentsById(boardId);

        return ResponseEntity.status(HttpStatus.OK) .body(ResponseUtil.createSuccessResponse(response));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "특정 댓글의 자식 댓글(후손 아님!)을 조회하는 API",
            description = "pathvariable 로 받은 댓글의 id를 기준으로 모든 자식 댓글이 조회됩니다"
    )
    public ResponseEntity<ResponseBody<List<CommentDTO>>> getChildCommentsById(@PathVariable Long id ){
        List<CommentDTO>  response = commentService.getChildCommentsById(id);
        return ResponseEntity.status(HttpStatus.OK) .body(ResponseUtil.createSuccessResponse(response));
    }





    @PostMapping("")
    @Operation(
            summary = "특정 게시물에 댓글을 다는 API",
            description = "request body 로 받은 데이터를 기반으로 댓글이 생성됩니다. 보내주셔야하는 필드는 아래와 같습니다.\n\n"+
                    " Long boardId\n\nLong memberId\n\nLong parentId(nullable)\n\nString content\n\nInteger likes(nullable)\n\n"
    )
    public ResponseEntity<ResponseBody<CommentDTO>> createComment(@Valid @RequestBody CommentDTO dto){
            CommentDTO createdDto = commentService.createComment(dto.getMemberId(), dto.getBoardId(),dto);

        return ResponseEntity.status(HttpStatus.OK) .body(ResponseUtil.createSuccessResponse(createdDto));
    }


    @PatchMapping("/{id}")
    @Operation(
            summary = "특정 댓글을 업데이트하는 API",
            description = "pathvariable 로 받은 댓글의 id와 request body 로 받은 데이터를 기반으로 댓글이 수정됩니다."

    )
    public ResponseEntity<ResponseBody<CommentDTO>> patchComment(@PathVariable Long id,@Valid @RequestBody CommentDTO dto){
        log.info(id+", "+dto);
        CommentDTO updated = commentService.patchComment(id, dto);
        return   ResponseEntity.status(HttpStatus.OK) .body(ResponseUtil.createSuccessResponse(updated));
    }


    @DeleteMapping("/{id}")
    @Operation(
            summary = "특정 댓글을 삭제하는 API",
            description = "pathvariable 로 받은 게시물의 id를 기반으로 댓글이 삭제됩니다."
    )
    public ResponseEntity deleteComment(@PathVariable Long id){
            commentService.deleteComment(id);
        return ResponseEntity.status(HttpStatus.OK) .body(ResponseUtil.createSuccessResponse("성공적으로 삭제됨"));
    }





}
