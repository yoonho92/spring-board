package test.board.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SimplePost {
    private Long id;
    private String title;
    private String author;
    private String content;
}
