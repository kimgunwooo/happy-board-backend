package likelion.ideateam3.happy_board.dto;

import likelion.ideateam3.happy_board.domain.comment.Comment;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class CommentDTO {


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

    public static CommentDTO toDTO(Comment comment){
        return  new CommentDTO(comment.getId(), comment.getBoard().getId()
                ,  comment.getParent() ==null ? null: comment.getParent().getId()
                ,  comment.getContent(), comment.getLikes());
    }

}
