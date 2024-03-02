package io.muzoo.ssc.activityportal.backend.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

@RestController
public class AuthenticationController {
    @GetMapping("/api/test")
    public String test() {
        return "Login successful if this text is shown";
    }

    @PostMapping("/api/login")
    public String login(HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try{
            request.login(username, password);
            return "Login successful";
        } catch (ServletException e) {
            return "Failed to login";
        }
    }

    @GetMapping("/api/logout")
    public String logout(HttpServletRequest request) {
        try {
            request.logout();
            return "Logout successful";
        } catch (ServletException e) {
            e.printStackTrace();
            return "Logout failed";
        }
    }
}
