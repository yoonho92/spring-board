package test.board.configuration;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import test.board.dto.common.UserStatusForSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class HttpInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        HttpSession session = request.getSession(false);

        if (session != null) {
            UserStatusForSession userStatus = (UserStatusForSession) session.getAttribute("userStatus");
            if(userStatus != null){
                return true;
            }
        }

        String requestURI = request.getRequestURI();
        response.sendRedirect("/board/auth/signin?redirectURL=" + requestURI);

        return false;
    }

}
