package test.board.post.dto;

import lombok.Data;
import test.board.post.Post;

@Data
public class ReqForPost {
    Long id;
    String author;
    String title;
    String content;

    public static Post toPost(ReqForPost req){
        Post post = new Post();

        post.setAuthor(req.getAuthor());
        post.setTitle(req.getTitle());
        post.setContent(req.getContent());

        return post;
    }

}
