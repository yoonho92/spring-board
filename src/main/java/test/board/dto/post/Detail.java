package test.board.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import test.board.entity.Comment;

import java.time.OffsetDateTime;
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
    private OffsetDateTime date;
    private boolean isOwner;
}
