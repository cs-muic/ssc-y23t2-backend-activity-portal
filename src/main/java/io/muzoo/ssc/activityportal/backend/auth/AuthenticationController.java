package io.muzoo.ssc.activityportal.backend.auth;

import io.muzoo.ssc.activityportal.backend.SimpleResponseDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    @GetMapping("/api/test")
    public SimpleResponseDTO test() {
        return SimpleResponseDTO
                .builder()
                .success(true)
                .message("Login is successful if this text is visible")
                .build();
    }

    @PostMapping("/api/login")
    public SimpleResponseDTO login(HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try{
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(principal != null && principal instanceof User) {
                request.logout();
            }
            request.login(username, password);
            return SimpleResponseDTO
                    .builder()
                    .success(true)
                    .message("Login successful")
                    .build();
        } catch (ServletException e) {
            return SimpleResponseDTO
                    .builder()
                    .success(false)
                    .message(e.getMessage())
                    .build();
        }
    }

    @GetMapping("/api/logout")
    public SimpleResponseDTO logout(HttpServletRequest request) {
        try {
            request.logout();
            return SimpleResponseDTO
                    .builder()
                    .success(true)
                    .message("Logout successful")
                    .build();
        } catch (ServletException e) {
            return SimpleResponseDTO
                    .builder()
                    .success(false)
                    .message(e.getMessage())
                    .build();
        }
    }
}
