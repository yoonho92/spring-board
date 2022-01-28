package test.board.comment;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import test.board.dto.comment.SimpleComment;
import test.board.dto.common.UserStatusForSession;
import test.board.entity.Comment;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class CommentServiceTest {
    @Mock
    CommentRepository commentRepository;
    @InjectMocks
    CommentService commentService;
    List<Comment> comments = new ArrayList<>();

    @BeforeEach
    public void commentMockSetUp() {
        comments = LongStream.range(1, 100).mapToObj(commentId -> {
                    Comment comment = new Comment();
                    comment.setId(commentId);
                    comment.setAuthor("author" + commentId);
                    comment.setContent("content" + commentId);
                    comment.setPostId(commentId / 10);
                    comment.setAccountId(commentId / 10);
                    comment.setSubComments(
                            LongStream.range(1, commentId / 10).mapToObj(subCommentId -> {
                                        Comment subComment = new Comment();
                                        subComment.setId(subCommentId);
                                        subComment.setAuthor("author" + subCommentId);
                                        subComment.setContent("content" + subCommentId);
                                        subComment.setParentId(commentId);
                                        subComment.setAccountId(subCommentId / 10);

                                        return subComment;
                                    })
                                    .collect(Collectors.toList())

                    );
                    return comment;
                })
                .collect(Collectors.toList());
    }

    @DisplayName("불러온 comment의 목록이 session id와 일치하다.")
    @Test
    void findAllByPostIdAndSession() {
        Long postId = 9L;
        UserStatusForSession session = new UserStatusForSession();
        session.setAccountId(postId);

        Mockito.when(commentRepository.findAllByPostId(postId))
                .thenReturn(comments.stream().filter(comment -> comment.getPostId() == postId).collect(Collectors.toList()));

        List<SimpleComment> simpleComments = commentService.findByPostIdAndSession(postId, session);

        assertEquals(simpleComments.size(), 10);

        simpleComments.iterator().forEachRemaining(simpleComment -> {
            assertTrue(simpleComment.isOwner());
            assertEquals(simpleComment.getSimpleSubComments().size(), postId - 1);
            simpleComment
                    .getSimpleSubComments()
                    .iterator()
                    .forEachRemaining(simpleSubComment -> {
                        assertEquals(simpleSubComment.getParentId(), simpleComment.getId());
                    });
        });
    }

    @DisplayName("comment가 존재하지않는 Post의 Id로 비어있는 comment를 불러오다.")
    @Test
    void findByNotExistPostId() {
        Long postId = 0L;
        Long accountId = 0L;

        UserStatusForSession session = new UserStatusForSession();
        session.setAccountId(accountId);

        Mockito.when(commentRepository.findAllByPostId(postId))
                .thenReturn(Collections.emptyList());

        List<SimpleComment> simpleComments = commentService.findByPostIdAndSession(postId, session);

        assertEquals(simpleComments, Collections.emptyList());
    }

    @DisplayName("불러온 comment의 목록이 session id와 불일치하다.")
    @Test
    void findByPostIdAndNotEqualSession() {
        Long postId = 9L;
        UserStatusForSession session = new UserStatusForSession();
        session.setAccountId(1L);

        Mockito.when(commentRepository.findAllByPostId(postId))
                .thenReturn(comments.stream().filter(comment -> comment.getPostId() == postId).collect(Collectors.toList()));

        List<SimpleComment> simpleComments = commentService.findByPostIdAndSession(postId, session);

        simpleComments.iterator().forEachRemaining(simpleComment -> {
            assertFalse(simpleComment.isOwner());
            assertEquals(simpleComment.getSimpleSubComments().size(), postId - 1);
            simpleComment
                    .getSimpleSubComments()
                    .iterator()
                    .forEachRemaining(simpleSubComment -> {
                        assertEquals(simpleSubComment.getParentId(), simpleComment.getId());
                    });
        });
    }
}