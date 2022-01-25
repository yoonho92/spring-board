package test.board.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.board.entity.Post;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByTitleContaining(String title, Pageable pageable);
    List<Post> findAllByAccountIdAndDateBetween(Long accountId, OffsetDateTime beginDate, OffsetDateTime endDate);
}
