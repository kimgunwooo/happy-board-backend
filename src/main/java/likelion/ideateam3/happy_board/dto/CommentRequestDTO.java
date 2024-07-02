package likelion.ideateam3.happy_board.dto;

import likelion.ideateam3.happy_board.domain.comment.Comment;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class CommentRequestDTO {


    private Long id;
    private Long boardId;
    private Long parentId;
    private String content;
    private Integer likes;


    public Comment toEntity(){
        Comment entity = new Comment(this.id,this.content, this.likes);
        log.info("CommentDTO - toEntity() >> "+entity.toString());
        return entity;
    }

    public static CommentRequestDTO toDTO(Comment comment){
        return  new CommentRequestDTO(comment.getId(), comment.getBoard().getId()
                ,  comment.getParent() ==null ? null: comment.getParent().getId()
                ,  comment.getContent(), comment.getLikes());
    }

}
