package test.board.comment;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import test.board.comment.dto.DefaultForComment;
import test.board.comment.dto.ReqForComment;

@RestController
@AllArgsConstructor
@RequestMapping("/board/comment")
public class CommentController {
    private final CommentService commentService;

    @DeleteMapping(value = "/{id}/delete")
    public void deleteById(@PathVariable("id") Long id){
         commentService.deleteByIdForHierarchicalComment(id);
    }

    @PostMapping("/save")
    public DefaultForComment saveComment(@RequestBody ReqForComment req){
        System.out.println(req);

        return commentService.saveForDefault(req);
    }

}
