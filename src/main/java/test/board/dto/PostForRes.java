package test.board.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import test.board.comment.Comment;

import java.util.List;

@Data
@NoArgsConstructor
public class PostForRes {
    private Long id;

    private String title;

    private String author;

    private String content;

    private List<Comment> comments;

}
