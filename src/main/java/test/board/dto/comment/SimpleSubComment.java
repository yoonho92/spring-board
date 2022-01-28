package test.board.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleSubComment {
    private Long id = -1L;
    private String content = "";
    private String author = "";
    private boolean isOwner = false;
    private Long parentId = -1L;

}
