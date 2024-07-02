package likelion.ideateam3.happy_board.controller.comment;



import jakarta.validation.Valid;
import likelion.ideateam3.happy_board.dto.CommentRequestDTO;
import likelion.ideateam3.happy_board.dto.CommentResponseDTO;
import likelion.ideateam3.happy_board.response.ResponseBody;
import likelion.ideateam3.happy_board.response.ResponseUtil;
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
public class CommentController {
    private final CommentService commentService;

    @DeleteMapping("/comments/{boardId}")
    public ResponseEntity<ResponseBody<String>>  deleteAllComment(@PathVariable Long boardId){
             commentService.deleteByBoardId(boardId);
        return  ResponseEntity.status(HttpStatus.OK) .body(ResponseUtil.createSuccessResponse("정상적으로 삭제됨"));
    }

    @GetMapping("/comments/{boardId}")
    public ResponseEntity<ResponseBody<List<CommentResponseDTO>>> getCommentsById(@PathVariable Long boardId ){
            List<CommentResponseDTO>  response = commentService.getCommentsById(boardId);

        return ResponseEntity.status(HttpStatus.OK) .body(ResponseUtil.createSuccessResponse(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBody<List<CommentResponseDTO>>> getChildCommentsById(@PathVariable Long id ){
        List<CommentResponseDTO>  response = commentService.getChildCommentsById(id);
        return ResponseEntity.status(HttpStatus.OK) .body(ResponseUtil.createSuccessResponse(response));
    }





    @PostMapping("")
    public ResponseEntity<ResponseBody<CommentResponseDTO>> createComment(@Valid @RequestBody CommentRequestDTO dto){


// 멤버 정보를 따로 받지 않고 SecurityContext 에 저장된 authentication 객체를 활용


        CommentResponseDTO createdDto = commentService.createComment(dto);

        return ResponseEntity.status(HttpStatus.OK) .body(ResponseUtil.createSuccessResponse(createdDto));
    }


    @PatchMapping("/{id}")
    public ResponseEntity<ResponseBody<CommentResponseDTO>> patchComment(@PathVariable Long id, @Valid @RequestBody CommentRequestDTO dto){
        log.info(id+", "+dto);
        CommentResponseDTO updated = commentService.patchComment(id, dto);
        return   ResponseEntity.status(HttpStatus.OK) .body(ResponseUtil.createSuccessResponse(updated));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity deleteComment(@PathVariable Long id){
            commentService.deleteComment(id);
        return ResponseEntity.status(HttpStatus.OK) .body(ResponseUtil.createSuccessResponse("성공적으로 삭제됨"));
    }





}
