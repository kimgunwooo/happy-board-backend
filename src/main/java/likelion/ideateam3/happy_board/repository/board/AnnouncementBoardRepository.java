package likelion.ideateam3.happy_board.repository.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import likelion.ideateam3.happy_board.domain.board.AnnouncementBoard;

@Repository
public interface AnnouncementBoardRepository extends JpaRepository<AnnouncementBoard, Long> {
}
