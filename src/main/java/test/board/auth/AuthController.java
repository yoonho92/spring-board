package test.board.auth;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import test.board.auth.dto.ReqForSign;
import test.board.auth.dto.UserStatus;

import javax.servlet.http.HttpServletRequest;

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
    public UserStatus signIn(
            @RequestBody ReqForSign req,
            HttpServletRequest httpRequest
    ) {
        return authService.signIn(req, httpRequest);
    }

    @GetMapping("/signup")
    public String signUp() {
        return "sign_up";
    }

    @ResponseBody
    @PostMapping("/signup")
    public UserStatus signUp(
            @RequestBody ReqForSign req,
            HttpServletRequest httpRequest) {
        return authService.signUp(req, httpRequest);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest httpRequest){
        authService.logout(httpRequest);
    }

}
