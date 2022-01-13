package test.board.post;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import test.board.dto.UserStatusForSession;
import test.board.post.dto.Detail;
import test.board.post.dto.DefaultForPost;

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

    private Post save(Post post) { return postRepository.save(post); }
    private void deleteById(Long id) { postRepository.deleteById(id); }

    private Post findById(Long id) {
        return postRepository
                .findById(id)
                .orElseGet(Post::new);
    }

    public Page<DefaultForPost> searchByTitle(String title, Pageable pageable) {
        return postRepository.findByTitleContaining(title, pageable).map((post -> {
            return new DefaultForPost(
                    post.getId(),
                    post.getAuthor(),
                    post.getTitle());
        }));
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

    public DefaultForPost saveForDefault(Post post) {
        Post savedPost = this.save(post);

        return new DefaultForPost(
                savedPost.getId(),
                savedPost.getTitle(),
                savedPost.getAuthor()
        );
    }

    public DefaultForPost edit(Post post, UserStatusForSession userStatus) {
        Post foundPost = this.findById(post.getId());

        if (Objects.equals(foundPost.getAccountId(), userStatus.getAccountId())) {
            return this.saveForDefault(post);
        } else {
            logger.warning("post.accountId not equal userStatus.accountId");
        }

        return new DefaultForPost(null, null, null);
    }

    public Detail findDetailById(Long id) {
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

    public void deleteByIdAndSession(Long id, UserStatusForSession userStatus) {
        Post post = this.findById(id);

        if (Objects.equals(post.getAccountId(), userStatus.getAccountId())) {
            this.deleteById(id);
        } else {
            logger.warning("post.accountId not equal userStatus.accountId");
        }
    }
}

