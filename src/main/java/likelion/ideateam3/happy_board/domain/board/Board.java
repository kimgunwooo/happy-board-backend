package likelion.ideateam3.happy_board.domain.board;


import jakarta.persistence.*;
import likelion.ideateam3.happy_board.domain.comment.Comment;
import likelion.ideateam3.happy_board.domain.common.BaseEntity;
import likelion.ideateam3.happy_board.domain.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "board_type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public abstract class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(nullable = false)
    protected String title;

    @Column(nullable = false)
    protected String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "board" , cascade = CascadeType.ALL) // 게시판이 없어지면 게시판의 댓글도 모두 없어져야
    private List<Comment> comments = new ArrayList<>();

    public Board(String title, String content, Member member) {
        this.title = title;
        this.content = content;
        this.member = member;
    }
}
