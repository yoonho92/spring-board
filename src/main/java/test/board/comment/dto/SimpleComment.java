package test.board.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SimpleComment {
    private Long id;
    private String content;
    private String author;
}
