package test.board.post;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import test.board.dto.Pagination;
import test.board.dto.UserStatusForSession;
import test.board.post.dto.DefaultPost;
import test.board.post.dto.Detail;
import test.board.post.dto.ReqForPost;

import java.time.LocalDate;
import java.util.List;

@Controller
@AllArgsConstructor
@SessionAttributes("userStatus")
@RequestMapping("/board")
public class PostController {
    private final PostService boardService;

    @GetMapping
    public String searchPost(
            Model model,
            @RequestParam(required = false, defaultValue = "") String title,
            @RequestParam(name = "page", defaultValue = "1", required = false) int page,
            @RequestParam(name = "limit", defaultValue = "10", required = false) int limit,
            @ModelAttribute("userStatus") UserStatusForSession userStatus) {

        Page<DefaultPost> pages = boardService.searchByTitle(title, PageRequest.of(page - 1, limit, Sort.Direction.DESC, "id"));
        model.addAttribute("posts", pages);
        model.addAttribute("title", title);
        model.addAttribute("pagination", new Pagination(pages, page - 1));

        return "index";
    }

    @ResponseBody
    @GetMapping("/posts")
    public List<Detail> findAllByAccountIdAndPeriodDate(
            @RequestParam(name = "accountId", required = false, defaultValue = "") Long accountId,
            @RequestParam(name = "beginDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate begin,
            @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end){

        return boardService.findAllByAccountIdAndPeriodDate(accountId, begin, end);
    }

    @GetMapping("/{id}")
    public String findPostById(
            Model model,
            @ModelAttribute("userStatus") UserStatusForSession userStatus,
            @PathVariable("id") Long id) {
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
    public DefaultPost savePost(@RequestBody ReqForPost req) {
        return boardService.saveForDefault(req);
    }

    @GetMapping("/{id}/edit")
    public String editPostById(
            Model model,
            @PathVariable("id") Long id) {
        model.addAttribute("post", boardService.findDetailById(id));

        return "post_edit";
    }

    @ResponseBody
    @PatchMapping(value = "/{id}/edit")
    public DefaultPost editPostById(
            @RequestBody ReqForPost req,
            @SessionAttribute("userStatus") UserStatusForSession userStatus) {
        return boardService.edit(req, userStatus);
    }

    @ResponseBody
    @DeleteMapping(value = "/{id}/delete")
    public void deletePostById(
            @PathVariable("id") Long id,
            @SessionAttribute("userStatus") UserStatusForSession userStatus) {
        boardService.deleteByIdAndSession(id, userStatus);
    }

}
