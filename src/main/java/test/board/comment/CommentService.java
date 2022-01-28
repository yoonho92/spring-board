package test.board.comment;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import test.board.dto.comment.SimpleComment;
import test.board.dto.comment.ReqForComment;
import test.board.dto.comment.SimpleSubComment;
import test.board.dto.common.UserStatusForSession;
import test.board.entity.Comment;;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    private Comment save(Comment comment, UserStatusForSession session) throws Exception {
        if (Objects.equals(comment.getAccountId(), session.getAccountId())) {
            return commentRepository.save(comment);
        } else throw new Exception();
    }

    private void deleteById(Long id) {
        commentRepository.deleteById(id);
    }

    private Comment findById(Long id) {
        return commentRepository
                .findById(id)
                .orElseGet(Comment::new);
    }

    public List<SimpleComment> findByPostIdAndSession(Long postId, UserStatusForSession session) {
        return commentRepository
                .findAllByPostId(postId)
                .stream()
                .map(comment -> {
                    return new SimpleComment(
                            comment.getId(),
                            comment.getContent(),
                            comment.getAuthor(),
                            Objects.equals(comment.getAccountId(), session.getAccountId()),
                            comment.getIsDeleted(),
                            this.commentComposer(comment, session)
                    );
                })
                .collect(Collectors.toList());
    }

    public SimpleComment saveForSimple(ReqForComment req, UserStatusForSession session) throws Exception {
        Comment comment = this.save(ReqForComment.toComment(req), session);

        return new SimpleComment(
                comment.getId(),
                comment.getContent(),
                comment.getAuthor(),
                Objects.equals(comment.getAccountId(), session.getAccountId()),
                false,
                this.commentComposer(comment, session)
        );
    }

    public void deleteByIdForHierarchicalComment(Long id, UserStatusForSession session) {
        commentRepository.findById(id).map((comment) -> {
            if (comment.getSubComments().isEmpty()) {
                this.deleteById(id);

                if (comment.getIsPresentParent()) {
                    Comment parentComment = this.findById(comment.getParentId());

                    if (parentComment.getIsDeleted() && parentComment.getSubComments().isEmpty()) {
                        this.deleteById(parentComment.getId());
                    }
                }
            } else {
                comment.setContent("삭제된 댓글입니다.");
                comment.setIsDeleted(true);
                try {
                    this.save(comment, session);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return comment;
        });
    }

    private List<SimpleSubComment> commentComposer(Comment ParentComment, UserStatusForSession session) {
        return ParentComment
                .getSubComments()
                .stream()
                .map(subComment -> {
                    return new SimpleSubComment(
                            subComment.getId(),
                            subComment.getAuthor(),
                            subComment.getContent(),
                            Objects.equals(subComment.getAccountId(), session.getAccountId()),
                            ParentComment.getId()
                    );
                })
                .collect(Collectors.toList());
    }

}
