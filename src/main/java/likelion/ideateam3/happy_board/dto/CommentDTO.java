package likelion.ideateam3.happy_board.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import likelion.ideateam3.happy_board.domain.comment.Comment;
import lombok.*;
import lombok.extern.slf4j.Slf4j;


@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Setter
@Getter
@Slf4j
@Schema(name="CommentDetail")
public class CommentDTO {


    private Long id;
    private Long boardId;
    private Long memberId;
    private Long parentId;
    private String content;
    private Integer likes;


    public Comment toEntity(){
        Comment entity = new Comment(this.id,this.content, this.likes);
        log.info("CommentDTO - toEntity() >> "+entity.toString());
        return entity;
    }

    public static CommentDTO toDTO(Comment comment){
        return  new CommentDTO(comment.getId(), comment.getBoard().getId() ,comment.getMember().getId()
                ,  comment.getParent() ==null ? null: comment.getParent().getId()
                ,  comment.getContent(), comment.getLikes());
    }

}
