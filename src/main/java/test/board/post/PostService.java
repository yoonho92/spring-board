package test.board.post;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import test.board.dto.UserStatusForSession;

import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final Logger logger;

    public Post save(Post post){
        return postRepository.save(post);
    }

    public Post edit(Post post, UserStatusForSession userStatus){
        Optional<Post> foundPost = postRepository.findById(post.getId());

        if(foundPost.isPresent()){
            if(Objects.equals(foundPost.get().getAccountId(), userStatus.getAccountId())){

                return postRepository.save(post);
            }else {
                logger.warning("post.accountId not equal userStatus.accountId");
            }
        }else{
            logger.warning("Post not exist");
        }
        return post;
    }

    public Post findPostById(Long id){
        Optional<Post> post = postRepository.findById(id);

        if(post.isPresent()){
            return post.get();
        }else{
            logger.warning("Post not exist");
            return new Post();
        }
    }

    public void deletePostById(Long id, UserStatusForSession userStatus) {
        Optional<Post> post = postRepository.findById(id);

        if(post.isPresent()){
            if(Objects.equals(post.get().getAccountId(), userStatus.getAccountId())){

                postRepository.deleteById(id);
            }else {
                logger.warning("post.accountId not equal userStatus.accountId");
            }
        }else{
            logger.warning("Post not exist");
        }
    }

    public Page<Post> searchByTitle(String title, Pageable pageable){
        return postRepository.findByTitleContaining(title, pageable);
    }

}
