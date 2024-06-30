package likelion.ideateam3.happy_board.controller.comment;



import jakarta.validation.Valid;
import likelion.ideateam3.happy_board.dto.CommentDTO;
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

    @GetMapping("/comments/{boardId}")
    public ResponseEntity<ResponseBody<List<CommentDTO>>> getCommentsById(@PathVariable Long boardId ){
            List<CommentDTO>  response = commentService.getCommentsById(boardId);

        return ResponseEntity.status(HttpStatus.OK) .body(ResponseUtil.createSuccessResponse(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBody<List<CommentDTO>>> getChildCommentsById(@PathVariable Long id ){
        List<CommentDTO>  response = commentService.getChildCommentsById(id);
        return ResponseEntity.status(HttpStatus.OK) .body(ResponseUtil.createSuccessResponse(response));
    }





    @PostMapping("")
    public ResponseEntity<ResponseBody<CommentDTO>> createComment( @Valid @RequestBody CommentDTO dto){


// 멤버 정보를 따로 받지 않고 SecurityContext 에 저장된 authentication 객체를 활용


            CommentDTO createdDto = commentService.createComment(dto);

        return ResponseEntity.status(HttpStatus.OK) .body(ResponseUtil.createSuccessResponse(createdDto));
    }


    @PatchMapping("/{id}")
    public ResponseEntity<ResponseBody<CommentDTO>> patchComment(@PathVariable Long id,@Valid @RequestBody CommentDTO dto){
        log.info(id+", "+dto);
        CommentDTO updated = commentService.patchComment(id, dto);
        return   ResponseEntity.status(HttpStatus.OK) .body(ResponseUtil.createSuccessResponse(updated));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity deleteComment(@PathVariable Long id){
            commentService.deleteComment(id);
        return ResponseEntity.status(HttpStatus.OK) .body(ResponseUtil.createSuccessResponse("성공적으로 삭제됨"));
    }





}
