package likelion.ideateam3.happy_board.repository.board;

import java.util.List;

import likelion.ideateam3.happy_board.domain.board.HappyBoard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HappyBoardRepository extends JpaRepository<HappyBoard, Long> {
	List<HappyBoard> findByHazardFalse();
	List<HappyBoard> findByHazardTrueAndMemberId(Long memberId);

	List<HappyBoard> findByHazardFalseAndMemberId(Long memberId);
}
