package test.board.auth;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import test.board.auth.dto.Sign;
import test.board.auth.dto.SignIn;
import test.board.auth.dto.SignUp;
import test.board.dto.UserStatusForSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
@AllArgsConstructor
public class AccountService {
    private final AccountRepository authRepository;

    private Account findByName(String name) { return authRepository.findByName(name); }
    private Account save(Account account) { return authRepository.save(account); }

    public SignUp signUp(Sign req, HttpSession session) {
        Account user = this.findByName(req.getName());

        SignUp resForSignUp = new SignUp();
        resForSignUp.setName(req.getName());
        resForSignUp.setDate(req.getDate());

        if (user == null) {
            resForSignUp.setState(SignUp.State.SIGNUP);

            Account newUser = new Account();
            newUser.setName(req.getName());
            newUser.setSecret(req.getSecret());

            this.save(newUser);

            signIn(req, session);
        } else {
            resForSignUp.setState(SignUp.State.EXIST);
        }

        return resForSignUp;
    }

    public SignIn signIn(Sign req, HttpSession session) {
        Account user = this.findByName(req.getName());
        SignIn resForSignIn = new SignIn();

        if (user != null) {
            resForSignIn.setName(user.getName());

            if (req.getSecret().equals(user.getSecret())) {
                resForSignIn.setState(SignIn.State.SIGNIN);
                resForSignIn.setId(user.getId());

                session.setAttribute(
                        "userStatus",
                        new UserStatusForSession(user.getId(), req.getName(), UserStatusForSession.State.SIGNING));
            } else {
                resForSignIn.setState(SignIn.State.FAIL);
            }
        } else {
            resForSignIn.setState(SignIn.State.NOTEXIST);
        }

        return resForSignIn;
    }

    public void logout(HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession();
        session.invalidate();
    }

}
