package likelion.ideateam3.happy_board.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import likelion.ideateam3.happy_board.domain.comment.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class CommentResponseDTO {


    private Long id;
    private Long boardId;
    private Long memberId;
    private String nickname;
    private Long parentId;
    private String content;
    private Integer likes;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;



    public static CommentResponseDTO toDTO(Comment comment){
        return  new CommentResponseDTO(comment.getId(), comment.getBoard().getId(), comment.getMember().getId()
                ,comment.getMember().getNickname()
                ,  comment.getParent() ==null ? null: comment.getParent().getId()
                ,  comment.getContent(), comment.getLikes()
                , comment.getCreatedAt());
    }

}
