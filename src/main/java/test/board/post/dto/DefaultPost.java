package test.board.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DefaultPost {
    private Long id;
    private String title;
    private String author;
}
