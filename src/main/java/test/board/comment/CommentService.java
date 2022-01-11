package test.board.comment;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import test.board.comment.Comment;
import test.board.comment.CommentRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public Comment saveComment(Comment comment){
        return commentRepository.save(comment);
    }

    public List<Comment> findByPostId(String postId){
        return commentRepository.findByPostId(postId);
    }
    public List<Comment> findAll(){
        return commentRepository.findAll();
    }

    public Comment deleteById(Long id) {
        return commentRepository.findById(id)
                .stream().map((comment) -> {
                   if(comment.subComments.isEmpty()){
                       commentRepository.deleteById(id);

                       if(comment.getIsPresentParent()){
                           Comment parentComment = commentRepository.findById(comment.getParentId()).get();
                           if(parentComment.getIsDeleted() && parentComment.getSubComments().isEmpty()){
                               commentRepository.deleteById(parentComment.getId());
                           }
                       }
                   }else{
                       comment.setContent("삭제된 댓글입니다.");
                       comment.setIsDeleted(true);
                       commentRepository.save(comment);
                   }
                   return comment;
                })
                .findFirst()
                .orElse(new Comment());
    }
}
