package likelion.ideateam3.happy_board.repository.member;

import likelion.ideateam3.happy_board.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    Optional<Member> findByNickname(String nickname);
}
