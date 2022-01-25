package test.board.dto.post;

import lombok.Data;
import test.board.entity.Account;
import test.board.entity.Post;

@Data
public class ReqForPost {
    Long id;
    Long accountId;
    String author;
    String title;
    String content;

    public static Post toEntity(ReqForPost req){
        Post post = new Post();

        post.setId(req.getId());
        post.setAuthor(req.getAuthor());
        post.setTitle(req.getTitle());
        post.setContent(req.getContent());

        if(req.getAccountId() != null){
            Account account = new Account();
            account.setId(req.getAccountId());
            post.setAccount(account);
        }

        return post;
    }

}
