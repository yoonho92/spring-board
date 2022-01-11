package test.board.post;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post save(Post board){
        return postRepository.save(board);
    }

    public Page<Post> findAll(Pageable pageable){
        return postRepository.findAll(pageable);
    }

    public Post findPostById(Long id){
        return postRepository.findById(id).orElse(new Post());
    }

    public void deletePostById(Long id) { postRepository.deleteById(id);}

    public Page<Post> searchByTitle(String title, Pageable pageable){
        return postRepository.findByTitleContaining(title, pageable);
    }
}
