package likelion.ideateam3.happy_board.repository.comment;

import likelion.ideateam3.happy_board.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
