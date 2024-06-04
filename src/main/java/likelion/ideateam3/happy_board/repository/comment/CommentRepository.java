package likelion.ideateam3.happy_board.repository.comment;

import likelion.ideateam3.happy_board.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    void deleteByBoardId(Long boardId);
    List<Comment> findByBoardId(Long boardId);

    List<Comment> findByParentId(Long id);
}
