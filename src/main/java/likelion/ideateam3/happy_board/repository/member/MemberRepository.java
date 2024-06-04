package likelion.ideateam3.happy_board.repository.member;

import likelion.ideateam3.happy_board.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
