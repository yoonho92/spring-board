package test.board.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleComment {
    private Long id = -1L;
    private String content = "";
    private String author = "";
    private boolean isOwner = false;
    private boolean isDelete = false;
    private List<SimpleSubComment> simpleSubComments = new ArrayList<>();

}
