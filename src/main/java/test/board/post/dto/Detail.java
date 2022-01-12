package test.board.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import test.board.comment.Comment;

import java.util.List;

@Data
@AllArgsConstructor
public class Detail {
    private Long id;
    private Long accountId;
    private String title;
    private String author;
    private String content;
    private List<Comment> comments;
}
