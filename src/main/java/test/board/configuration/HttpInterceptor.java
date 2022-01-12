package test.board.configuration;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import test.board.dto.UserStatusForSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class HttpInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        HttpSession session = request.getSession(true);

        Object userStatus = session.getAttribute("userStatus");
        if (userStatus == null) {
            session.setAttribute(
                    "userStatus",
                    new UserStatusForSession(null, null, UserStatusForSession.State.NONE)
            );
        }

        return true;
    }

}
