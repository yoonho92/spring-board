package test.board.post;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import test.board.auth.dto.UserStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post save(Post post){
        return postRepository.save(post);
    }

    public Post edit(Post post){
        return postRepository.save(post);
    }


    public Post findPostById(Long id){
        return postRepository.findById(id).orElse(new Post());
    }

    public void deletePostById(Long id) { postRepository.deleteById(id);}

    public Page<Post> searchByTitle(String title, Pageable pageable){
        return postRepository.findByTitleContaining(title, pageable);
    }

    public void sessionInit(HttpServletRequest request){
        HttpSession httpSession = request.getSession();
        if(httpSession.getAttribute("userStatus") == null){
            httpSession.setAttribute("userStatus", new UserStatus(null, null, UserStatus.State.NONE));
        }
    }

}
