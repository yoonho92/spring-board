package test.board.post;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import test.board.dto.Pagination;
import test.board.dto.UserStatusForSession;
import test.board.post.dto.DefaultForPost;

@Controller
@AllArgsConstructor
@SessionAttributes("userStatus")
@RequestMapping("/board")
public class BoardController {
    private final PostService boardService;

    @GetMapping
    public String searchPost(
            Model model,
            @RequestParam(required = false, defaultValue = "") String title,
            @RequestParam(name = "page", defaultValue = "1", required = false) int page,
            @RequestParam(name = "limit", defaultValue = "10", required = false) int limit,
            @ModelAttribute("userStatus") UserStatusForSession userStatus) {
        Page<DefaultForPost> pages = boardService.searchByTitle(title, PageRequest.of(page - 1, limit, Sort.Direction.DESC, "id"));
        model.addAttribute("posts", pages);
        model.addAttribute("title", title);
        model.addAttribute("pagination", new Pagination(pages, page - 1));

        return "index";
    }

    @GetMapping("/{id}")
    public String findPostById(
            Model model,
            @ModelAttribute("userStatus") UserStatusForSession userStatus,
            @PathVariable("id") Long id
    ) {
        model.addAttribute("post", boardService.findDetailById(id));
        model.addAttribute("userStatus", userStatus);

        return "post_detail";
    }

    @GetMapping("/save")
    public String savePost(
            @ModelAttribute("userStatus") UserStatusForSession userStatus) {
        return "post_save";
    }

    @ResponseBody
    @PostMapping("/save")
    public DefaultForPost savePost(@RequestBody Post post) {
        return boardService.save(post);
    }

    @GetMapping("/{id}/edit")
    public String editPostById(
            Model model,
            @PathVariable("id") Long id
    ) {
        model.addAttribute("post", boardService.findDetailById(id));
        return "post_edit";
    }

    @ResponseBody
    @PatchMapping(value = "/{id}/edit")
    public DefaultForPost editPostById(
            @RequestBody Post post,
            @SessionAttribute("userStatus") UserStatusForSession userStatus) {
        return boardService.edit(post, userStatus);
    }

    @ResponseBody
    @DeleteMapping(value = "/{id}/delete")
    public void deletePostById(
            @PathVariable("id") Long id,
            @SessionAttribute("userStatus") UserStatusForSession userStatus
    ) {
        boardService.deletePostById(id, userStatus);
    }

}
