package test.board.auth;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import test.board.auth.dto.ReqForSign;
import test.board.auth.dto.ResForSignIn;
import test.board.auth.dto.ResForSignUp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @GetMapping("/signin")
    public String signIn() {
        return "sign_in";
    }

    @ResponseBody
    @PostMapping("/signin")
    public ResForSignIn signIn(
            @RequestBody ReqForSign req,
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
    public ResForSignUp signUp(
            @RequestBody ReqForSign req,
            HttpSession session) {
        return authService.signUp(req, session);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest httpRequest){
        authService.logout(httpRequest);
    }

}
