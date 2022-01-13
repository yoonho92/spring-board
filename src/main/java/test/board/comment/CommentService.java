package test.board.comment;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import test.board.comment.dto.SimpleComment;
import test.board.comment.dto.ReqForComment;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    private Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    private void deleteById(Long id) {
        commentRepository.deleteById(id);
    }

    private Comment findById(Long id) {
        return commentRepository
                .findById(id)
                .orElseGet(Comment::new);
    }

    public SimpleComment saveForDefault(ReqForComment req) {
        Comment savedComment = this.save(ReqForComment.toComment(req));

        return new SimpleComment(
                savedComment.getId(),
                savedComment.getContent(),
                savedComment.getAuthor()
        );
    }

    public void deleteByIdForHierarchicalComment(Long id) {
        commentRepository.findById(id).map((comment) -> {
            if (comment.subComments.isEmpty()) {
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
                this.save(comment);
            }
            return comment;
        });
    }

}
