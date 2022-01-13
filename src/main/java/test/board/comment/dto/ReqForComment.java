package test.board.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import test.board.auth.Account;
import test.board.comment.Comment;
import test.board.post.Post;

@Data
public class ReqForComment {
    String author;
    String content;
    Long postId;
    Long accountId;
    Long parentId;
    boolean isPresentParent;

    public static Comment toComment(ReqForComment req){
        Comment comment = new Comment();

        comment.setAuthor(req.getAuthor());
        comment.setContent(req.getContent());
        comment.setIsPresentParent(req.isPresentParent());

        if(req.getPostId() != null){
            Post post =new Post();
            post.setId(req.getPostId());

            comment.setPost(post);
        }

        if(req.getAccountId() != null){
            Account account = new Account();
            account.setId(req.getAccountId());

            comment.setAccount(account);
        }

        if(req.getParentId() != null){
            Comment parentComment = new Comment();
            parentComment.setId(req.getParentId());

            comment.setParentComment(parentComment);
        }

        return comment;
    }

}
