package likelion.ideateam3.happy_board.domain.comment;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import likelion.ideateam3.happy_board.domain.board.Board;
import likelion.ideateam3.happy_board.domain.common.BaseEntity;
import likelion.ideateam3.happy_board.domain.member.Member;
import likelion.ideateam3.happy_board.dto.CommentDTO;
import likelion.ideateam3.happy_board.response.exception.BusinessException;
import likelion.ideateam3.happy_board.response.exception.ExceptionType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parent;

    @OneToMany(fetch = FetchType.LAZY ,mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Comment> children = new ArrayList<>(); // 루트 댓글 삭제시 대댓도 삭제됨

    @PositiveOrZero
    private Integer likes;

    // dto 의 toEntity() 용
    public Comment(Long id,String content, Integer likes){
        this.id = id;
        this.content = content;
        this.likes = likes;
    }



    // 이 클래스의 createEntityWithDto 용
    public Comment(Board board , Member member , Comment parent ,String content, Integer likes){
        this.board = board;
        this.member= member;
        this.parent = parent;
        this.content = content;
        this.likes = likes;
    }


    // DTO 에 정의한 toEntity 와는 별개로 생성
    // dto 의 toEntity 는 dto 정보만을 기반으로 entity 를 생성하는 것
    // 이번에 정의한 것은 db 기반으로 만드는 것
    public static Comment createEntityWithDto(CommentDTO dto, Board board , Member member , Comment parent) {
        // // 엔티티 생성 불가한 경우 (POST 메서드가 PATCH 기능하는거 방지하기 위해 )  예외 발생
      
        // 엔티티 생성 및 반환
        Comment comment = new Comment(board, member,parent , dto.getContent() , dto.getLikes());
        return comment;
    }

    public void patch(CommentDTO dto){ // CommentService 의 patch 전용
        if(dto.getContent() !=null) this.content = dto.getContent();
        if(dto.getLikes() !=null) this.likes = dto.getLikes();
    }

}
