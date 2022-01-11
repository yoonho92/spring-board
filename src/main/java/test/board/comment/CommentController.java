package test.board.comment;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import test.board.comment.Comment;
import test.board.comment.CommentService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/board/comment")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{postId}")
    public List<Comment> findByPostId(@PathVariable String postId){
        return commentService.findByPostId(postId);
    }

    @DeleteMapping(value = "/{id}/delete")
    public Comment deleteById(@PathVariable("id") Long id){
        return commentService.deleteById(id);
    }

    @PostMapping("/save")
    public Comment saveComment(
            @RequestBody Comment comment){
        return commentService.saveComment(comment);
    }

    @GetMapping("/list")
    public List<Comment> findAll(){
        return commentService.findAll();
    }
}
