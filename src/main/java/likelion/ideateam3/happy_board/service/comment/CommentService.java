package likelion.ideateam3.happy_board.service.comment;


import likelion.ideateam3.happy_board.domain.board.Board;
import likelion.ideateam3.happy_board.domain.comment.Comment;
import likelion.ideateam3.happy_board.domain.member.Member;
import likelion.ideateam3.happy_board.dto.CommentDTO;
import likelion.ideateam3.happy_board.repository.board.BoardRepository;
import likelion.ideateam3.happy_board.repository.comment.CommentRepository;
import likelion.ideateam3.happy_board.repository.member.MemberRepository;
import likelion.ideateam3.happy_board.response.exception.BusinessException;
import likelion.ideateam3.happy_board.response.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;


    @Transactional
    public void deleteByBoardId(Long boardId) {
        commentRepository.deleteByBoardId(boardId);
    }


    public List<CommentDTO> getCommentsById(Long boardId) {
        List<Comment> entityList= commentRepository.findByBoardId(boardId);
        List<CommentDTO> dtoList = entityList.stream().map(entity->CommentDTO.toDTO(entity)).collect(Collectors.toList());
        return dtoList;
    }

    public List<CommentDTO> getChildCommentsById(Long id) {
        List<Comment> entityList= commentRepository.findByParentId(id);
        List<CommentDTO> dtoList = entityList.stream().map(entity->CommentDTO.toDTO(entity)).collect(Collectors.toList());
        return dtoList;
    }

    @Transactional
    public CommentDTO createComment(Long memberId , Long boardId, CommentDTO dto) {
        log.info("createComment-input >> "+ memberId+", "+boardId+", "+dto.toString());
        // 1. 해당 postId 를 가지는 게시글이 존재하는지 우선 검색
        Board board = boardRepository.findById(boardId)
                .orElseThrow(()->   new BusinessException(ExceptionType.BOARD_NOT_FOUND_ERROR)); // post 없을 시 예외 발생

        log.info("createComment - boardRepository.findById(postId) >> "+board);

        // 2. 해당 id 를 가지는 사용자가 존재하는지 우선 검색
        Member member = memberRepository.findById(memberId).orElseThrow(()->  new BusinessException(ExceptionType.MEMBER_NOT_FOUND_ERROR));
        log.info("createComment - memberRepository.findById(memberId) >> "+member);


        // 3. 해당 commentId 를 가지는 부모 comment 가 존재하는지 우선 검색
        Comment parentComment = null;
        if(dto.getParentId() != null) {
            parentComment = commentRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new BusinessException(ExceptionType.COMMENT_NOT_FOUND_ERROR));// 부모 comment 없을 시 예외 발생
            log.info("createComment - commentRepository.findById(dto.getParentId()) >> " + parentComment);
        }
        // 4. 댓글 엔티티 생성
        Comment comment = Comment.createEntityWithDto(dto, board, member ,parentComment );
        Comment created = commentRepository.save(comment);
        return CommentDTO.toDTO(created);
    }



    @Transactional
    public CommentDTO patchComment(Long id, CommentDTO dto) {
        // 1. 해당 id 를 가지는 댓글이 존재하는지 우선 검색
        Comment comment = commentRepository.findById(id)
                .orElseThrow(()-> new BusinessException(ExceptionType.COMMENT_NOT_FOUND_ERROR));
        log.info("comment >> " + comment);

        //2. 더티체킹 기반 업데이트
        comment.patch(dto);
        log.info("patchComment >> " + comment);

        return CommentDTO.toDTO(comment);
    }

    @Transactional
    public void deleteComment(Long id) {
        // 1. 해당 id 를 가지는 댓글이 존재하는지 우선 검색
        Comment comment = commentRepository.findById(id)
                .orElseThrow(()->  new BusinessException(ExceptionType.COMMENT_NOT_FOUND_ERROR));
     
        //2. 해당 댓글 자체를 삭제 >> cascade 에 의해 이 댓글에 대한 대댓도 자동 delete 됨
        commentRepository.deleteById(id);



    }


}
