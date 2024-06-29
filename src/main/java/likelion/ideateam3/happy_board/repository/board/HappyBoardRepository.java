package likelion.ideateam3.happy_board.repository.board;

import likelion.ideateam3.happy_board.domain.board.HappyBoard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HappyBoardRepository extends JpaRepository<HappyBoard, Long> {
}
