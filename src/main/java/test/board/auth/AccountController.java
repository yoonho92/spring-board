package test.board.auth;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import test.board.auth.dto.Sign;
import test.board.auth.dto.SignIn;
import test.board.auth.dto.SignUp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
@RequestMapping("/board/auth")
public class AccountController {
    private final AccountService authService;

    @GetMapping("/signin")
    public String signIn() {
        return "sign_in";
    }

    @ResponseBody
    @PostMapping("/signin")
    public SignIn signIn(
            @RequestBody Sign req,
            HttpSession session
    ) {
        return authService.signIn(req, session);
    }

    @GetMapping("/signup")
    public String signUp() {
        return "sign_up";
    }

    @ResponseBody
    @PostMapping("/signup")
    public SignUp signUp(
            @RequestBody Sign req,
            HttpSession session) {
        return authService.signUp(req, session);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest httpRequest){
        authService.logout(httpRequest);
    }

}
