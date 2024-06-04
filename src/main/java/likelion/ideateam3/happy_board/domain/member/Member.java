package likelion.ideateam3.happy_board.domain.member;

import jakarta.persistence.*;
import likelion.ideateam3.happy_board.domain.board.Board;
import likelion.ideateam3.happy_board.domain.comment.Comment;
import likelion.ideateam3.happy_board.domain.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType role;

    @OneToMany(mappedBy = "member")
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "member" , cascade = CascadeType.ALL) // 멤버가 없어지면 멤버의 댓글도 모두 없어져야
    private List<Comment> comments = new ArrayList<>();
}


