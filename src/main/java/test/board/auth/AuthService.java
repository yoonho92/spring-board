package test.board.auth;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import test.board.auth.dto.ReqForSign;
import test.board.auth.dto.UserStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
@AllArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;

    public UserStatus signUp(ReqForSign req, HttpServletRequest httpRequest) {
        Account user = authRepository.findByName(req.getName());
        UserStatus userStatus = new UserStatus(0L, req.getName(), UserStatus.State.NONE);

        if (user == null) {
            userStatus.setState(UserStatus.State.SIGNUP);

            Account newUser = new Account();
            newUser.setName(req.getName());
            newUser.setSecret(req.getSecret());

            authRepository.save(newUser);

            signIn(req, httpRequest);
        } else {
            userStatus.setState(UserStatus.State.EXIST);
        }

        return userStatus;
    }

    public UserStatus signIn(ReqForSign req, HttpServletRequest httpRequest) {
        Account user = authRepository.findByName(req.getName());
        UserStatus userStatus = new UserStatus(0L, req.getName(), UserStatus.State.NONE);

        if (user != null) {
            if (req.getSecret().equals(user.getSecret())) {
                userStatus.setId(user.getId());
                userStatus.setState(UserStatus.State.SIGNING);

                HttpSession session = httpRequest.getSession();
                session.setAttribute("userStatus", userStatus);

            } else {
                userStatus.setState(UserStatus.State.FAIL);
            }
        } else {
            userStatus.setState(UserStatus.State.NOTEXIST);
        }

        return userStatus;
    }

    public void logout(HttpServletRequest httpRequest){
        HttpSession session = httpRequest.getSession();
        session.invalidate();
    }
}
