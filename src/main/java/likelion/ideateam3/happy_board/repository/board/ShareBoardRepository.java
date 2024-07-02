package likelion.ideateam3.happy_board.repository.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import likelion.ideateam3.happy_board.domain.board.ShareBoard;

@Repository
public interface ShareBoardRepository extends JpaRepository<ShareBoard, Long> {
}
