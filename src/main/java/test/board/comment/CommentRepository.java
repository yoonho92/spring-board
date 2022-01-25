package test.board.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.board.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
