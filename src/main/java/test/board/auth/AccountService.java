package test.board.auth;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import test.board.auth.dto.ReqForSign;
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

    public SignUp signUp(ReqForSign req, HttpSession session) {
        Account user = this.findByName(req.getName());

        SignUp res = new SignUp();
        res.setName(req.getName());

        if (user == null) {
            res.setState(SignUp.State.SIGNUP);

            Account newUser = new Account();
            newUser.setName(req.getName());
            newUser.setSecret(req.getSecret());

            this.save(newUser);

            signIn(req, session);
        } else {
            res.setState(SignUp.State.EXIST);
        }

        return res;
    }

    public SignIn signIn(ReqForSign req, HttpSession session) {
        Account user = this.findByName(req.getName());
        SignIn res = new SignIn();

        if (user != null) {
            res.setName(user.getName());

            if (req.getSecret().equals(user.getSecret())) {
                res.setState(SignIn.State.SIGNIN);
                res.setId(user.getId());

                session.setAttribute(
                        "userStatus",
                        new UserStatusForSession(user.getId(), req.getName(), UserStatusForSession.State.SIGNING));
            } else {
                res.setState(SignIn.State.FAIL);
            }
        } else {
            res.setState(SignIn.State.NOTEXIST);
        }

        return res;
    }

    public void logout(HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession();
        session.invalidate();
    }

}
