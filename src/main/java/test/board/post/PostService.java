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
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final Logger logger;

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

    public DefaultForPost save(Post post) {
        Post savedPost = postRepository.save(post);

        return new DefaultForPost(
                savedPost.getId(),
                savedPost.getTitle(),
                savedPost.getAuthor()
        );
    }

    public DefaultForPost edit(Post post, UserStatusForSession userStatus) {
        Optional<Post> foundPost = postRepository.findById(post.getId());

        if (foundPost.isPresent()) {
            if (Objects.equals(foundPost.get().getAccountId(), userStatus.getAccountId())) {
                Post savedPost = postRepository.save(post);

                return new DefaultForPost(
                        savedPost.getId(),
                        savedPost.getTitle(),
                        savedPost.getAuthor()
                );
            } else {
                logger.warning("post.accountId not equal userStatus.accountId");
            }
        } else {
            logger.warning("Post not exist");
        }
        return new DefaultForPost(null, null, null);
    }

    public Detail findDetailById(Long id) {
        Optional<Post> post = postRepository.findById(id);

        if (post.isPresent()) {

            return new Detail(
                    post.get().getId(),
                    post.get().getAccountId(),
                    post.get().getTitle(),
                    post.get().getAuthor(),
                    post.get().getContent(),
                    post.get().getComments(),
                    post.get().getDate()
            );
        } else {
            logger.warning("Post not exist");
            return new Detail(null, null, null, null, null, null, null);
        }
    }

    public void deleteById(Long id, UserStatusForSession userStatus) {
        Optional<Post> post = postRepository.findById(id);

        if (post.isPresent()) {
            if (Objects.equals(post.get().getAccountId(), userStatus.getAccountId())) {

                postRepository.deleteById(id);
            } else {
                logger.warning("post.accountId not equal userStatus.accountId");
            }
        } else {
            logger.warning("Post not exist");
        }
    }

}
