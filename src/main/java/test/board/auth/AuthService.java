package test.board.auth;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import test.board.auth.dto.ReqForSign;
import test.board.auth.dto.ResForSignIn;
import test.board.auth.dto.ResForSignUp;
import test.board.dto.UserStatusForSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
@AllArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;

    public ResForSignUp signUp(ReqForSign req, HttpSession session) {
        Account user = authRepository.findByName(req.getName());

        ResForSignUp resForSignUp = new ResForSignUp();
        resForSignUp.setName(req.getName());
        resForSignUp.setDate(req.getDate());

        if (user == null) {
            resForSignUp.setState(ResForSignUp.State.SIGNUP);

            Account newUser = new Account();
            newUser.setName(req.getName());
            newUser.setSecret(req.getSecret());

            authRepository.save(newUser);

            signIn(req, session);
        } else {
            resForSignUp.setState(ResForSignUp.State.EXIST);
        }

        return resForSignUp;
    }

    public ResForSignIn signIn(ReqForSign req, HttpSession session) {
        Account user = authRepository.findByName(req.getName());
        ResForSignIn resForSignIn = new ResForSignIn();

        if (user != null) {
            resForSignIn.setName(user.getName());

            if (req.getSecret().equals(user.getSecret())) {
                resForSignIn.setState(ResForSignIn.State.SIGNIN);
                resForSignIn.setId(user.getId());

                session.setAttribute(
                        "userStatus",
                        new UserStatusForSession(user.getId(), req.getName(), UserStatusForSession.State.SIGNING));
            } else {
                resForSignIn.setState(ResForSignIn.State.FAIL);
            }
        } else {
            resForSignIn.setState(ResForSignIn.State.NOTEXIST);
        }

        return resForSignIn;
    }

    public void logout(HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession();
        session.invalidate();
    }

}
