package test.board.post;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import test.board.Pagination;
import test.board.auth.dto.UserStatus;

import javax.servlet.http.HttpServletRequest;

@Controller
@AllArgsConstructor

@RequestMapping("/board")
public class BoardController {
    private final PostService boardService;

    @GetMapping
    public String searchPost(
            Model model,
            @RequestParam(required = false, defaultValue = "") String title,
            @RequestParam(name = "page", defaultValue = "1", required = false) int page,
            @RequestParam(name = "limit", defaultValue = "10", required = false) int limit,
            HttpServletRequest httpRequest) {
        boardService.sessionInit(httpRequest);
        Page<Post> pages = boardService.searchByTitle(title, PageRequest.of(page - 1, limit, Sort.Direction.DESC, "id"));
        model.addAttribute("posts", pages);
        model.addAttribute("title", title);
        model.addAttribute("pagination", new Pagination(pages, page - 1));
        model.addAttribute("userStatus", httpRequest.getSession().getAttribute("userStatus"));

        return "index";
    }

    @GetMapping("/{id}")
    public String findPostById(
            Model model,
            @SessionAttribute(value = "userStatus", required = false) UserStatus userStatus,
            @PathVariable("id") Long id
    ) {

        model.addAttribute("post", boardService.findPostById(id));
        model.addAttribute("userStatus", userStatus);

        return "post_detail";
    }

    @GetMapping("/save")
    public String savePost(
            Model model,
            @SessionAttribute(value = "userStatus", required = false) UserStatus userStatus) {
        model.addAttribute("userStatus", userStatus);

        return "post_save";
    }

    @ResponseBody
    @PostMapping("/save")
    public Post savePost(@RequestBody Post board) {
        return boardService.save(board);
    }

    @GetMapping("/{id}/edit")
    public String editPostById(
            Model model,
            @PathVariable("id") Long id
    ) {
        model.addAttribute("post", boardService.findPostById(id));
        return "post_edit";
    }

    @ResponseBody
    @PatchMapping(value = "/{id}/edit")
    public Post editPostById(@RequestBody Post post) {
        return boardService.edit(post);
    }

    @ResponseBody
    @DeleteMapping(value = "/{id}/delete")
    public void deletePostById(@PathVariable("id") Long id) {
        boardService.deletePostById(id);
    }

}
