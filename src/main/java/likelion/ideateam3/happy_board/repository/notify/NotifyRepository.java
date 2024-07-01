package likelion.ideateam3.happy_board.repository.notify;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import likelion.ideateam3.happy_board.domain.notify.Notify;

@Repository
public interface NotifyRepository extends JpaRepository<Notify, Long> {
}
