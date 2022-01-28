package test.board.comment;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import test.board.dto.comment.SimpleComment;
import test.board.dto.comment.ReqForComment;
import test.board.dto.common.UserStatusForSession;

@RestController
@AllArgsConstructor
@SessionAttributes("userStatus")
@RequestMapping("/board/comment")
public class CommentController {
    private final CommentService commentService;

    @DeleteMapping(value = "/{id}/delete")
    public void deleteById(
            @PathVariable("id") Long id,
            @SessionAttribute(value = "userStatus", required = false) UserStatusForSession session
    ) {
        commentService.deleteByIdForHierarchicalComment(id, session);
    }

    @PostMapping("/save")
    public SimpleComment saveComment(
            @RequestBody ReqForComment req,
            @SessionAttribute(value = "userStatus", required = false) UserStatusForSession session
    ) throws Exception {

        return commentService.saveForSimple(req, session);
    }

}
