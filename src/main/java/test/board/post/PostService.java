package test.board.post;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import test.board.dto.UserStatusForSession;
import test.board.post.dto.Detail;
import test.board.post.dto.SimplePost;
import test.board.post.dto.ReqForPost;

import java.time.*;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final Logger logger;

    private void deleteById(Long id) { postRepository.deleteById(id); }

    private Post findById(Long id) {
        return postRepository
                .findById(id)
                .orElseGet(Post::new);
    }

    public Page<SimplePost> searchByTitle(String title, Pageable pageable) {
        return postRepository.findByTitleContaining(title, pageable).map((post -> {
            return new SimplePost(
                    post.getId(),
                    post.getTitle(),
                    post.getAuthor(),
                    post.getContent());
        }));
    }

    public Detail findByIdForDetail(Long id) {
        Post post = this.findById(id);

        return new Detail(
                post.getId(),
                post.getAccountId(),
                post.getTitle(),
                post.getAuthor(),
                post.getContent(),
                post.getComments(),
                post.getDate()
        );
    }

    public SimplePost findByIdForEdit(Long id){
        Post post = this.findById(id);

        return new SimplePost(
                post.getId(),
                post.getTitle(),
                post.getAuthor(),
                post.getContent()
        );
    }

    public List<Detail> findAllByAccountIdAndPeriodDate(Long accountId, LocalDate beginDate, LocalDate endDate) {
        return postRepository.findAllByAccountIdAndDateBetween(
                accountId,
                OffsetDateTime.of(beginDate, LocalTime.MIN, ZoneOffset.UTC),
                OffsetDateTime.of(endDate, LocalTime.MAX, ZoneOffset.UTC)
        ).stream().map(post -> {
            return new Detail(
                    post.getId(),
                    post.getAccountId(),
                    post.getTitle(),
                    post.getAuthor(),
                    post.getContent(),
                    post.getComments(),
                    post.getDate()
            );
        }).collect(Collectors.toList());
    }

    public SimplePost save(ReqForPost req) {
        Post savedPost = postRepository.save(ReqForPost.toEntity(req));

        return new SimplePost(
                savedPost.getId(),
                savedPost.getTitle(),
                savedPost.getAuthor(),
                savedPost.getContent()
        );
    }

    public SimplePost edit(ReqForPost req, UserStatusForSession userStatus) {
        Post foundPost = this.findById(req.getId());

        if (Objects.equals(foundPost.getAccountId(), userStatus.getAccountId())) {
            return this.save(req);
        } else {
            logger.warning("post.accountId not equal userStatus.accountId");
        }

        return new SimplePost(null, null, null, null);
    }

    public void deleteByIdAndSession(Long id, UserStatusForSession userStatus) {
        Post post = this.findById(id);

        if (Objects.equals(post.getAccountId(), userStatus.getAccountId())) {
            this.deleteById(id);
        } else {
            logger.warning("post.accountId not equal userStatus.accountId");
        }
    }

}

