package test.board.post;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import test.board.dto.common.Pagination;
import test.board.dto.common.UserStatusForSession;
import test.board.dto.post.SimplePost;
import test.board.dto.post.Detail;
import test.board.dto.post.ReqForPost;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
            @SessionAttribute(value = "userStatus", required = false) UserStatusForSession userStatus
    ) {

        Page<SimplePost> pages = boardService.searchByTitle(title, PageRequest.of(page - 1, limit, Sort.Direction.DESC, "id"));
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
            @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

        return boardService.findAllByAccountIdAndPeriodDate(accountId, begin, end);
    }

    @GetMapping("/{id}")
    public String findPostById(
            Model model,
            @SessionAttribute(value = "userStatus", required = false) Optional<UserStatusForSession> userStatus,
            @PathVariable("id") Long id) {
        model.addAttribute("post", boardService.findByIdForDetail(id, userStatus));
        model.addAttribute("userStatus", userStatus.orElseGet(UserStatusForSession::new));

        return "post_detail";
    }

    @GetMapping("/save")
    public String savePost(
            @ModelAttribute("userStatus") UserStatusForSession userStatus) {
        return "post_save";
    }

    @ResponseBody
    @PostMapping("/save")
    public SimplePost savePost(
            @RequestBody ReqForPost req,
            @SessionAttribute("userStatus") UserStatusForSession userStatus) throws Exception {
        return boardService.savePost(req, userStatus);
    }

    @GetMapping("/{id}/edit")
    public String editPostById(
            Model model,
            @PathVariable("id") Long id) {
        model.addAttribute("post", boardService.findByIdForEdit(id));

        return "post_edit";
    }

    @ResponseBody
    @PatchMapping(value = "/{id}/edit")
    public SimplePost editPostById(
            @RequestBody ReqForPost req,
            @SessionAttribute("userStatus") UserStatusForSession session) throws Exception {
        return boardService.edit(req, session);
    }

    @ResponseBody
    @DeleteMapping(value = "/{id}/delete")
    public void deletePostById(
            @PathVariable("id") Long id,
            @SessionAttribute("userStatus") UserStatusForSession userStatus) {
        System.out.println(userStatus);
        boardService.deleteByIdAndSession(id, userStatus);
    }

    @ResponseBody
    @PostMapping("/session-data")
    public UserStatusForSession offerSession(@SessionAttribute(value = "userStatus", required = false) UserStatusForSession userStatus) {
        return Objects.requireNonNullElseGet(userStatus, () -> new UserStatusForSession(null, null, UserStatusForSession.State.NONE));
    }

}
