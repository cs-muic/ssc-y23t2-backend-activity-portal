package io.muzoo.ssc.activityportal.backend.user;

import io.muzoo.ssc.activityportal.backend.SimpleResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/api/user/{username}")
    public UserDTO viewProfile(@PathVariable("username") String username) {
        User u = userService.viewProfile(username);
        if (u != null) {
            return UserDTO.builder()
                    .success(true)
                    .user(u)
                    .message("User not found")
                    .build();
        } else {
            return UserDTO.builder()
                    .success(false)
                    .message("User not found")
                    .build();
        }
    }

    @PostMapping("/api/edit-profile")
    public SimpleResponseDTO editProfile(@RequestBody User user) {
        if (userService.editProfile(user)) {
            return SimpleResponseDTO.builder()
                    .success(true)
                    .message("Profile edited successfully")
                    .build();
        } else {
            return SimpleResponseDTO.builder()
                    .success(false)
                    .message("Failed to edit profile")
                    .build();
        }
    }

    @PostMapping("/api/create-account")
    public SimpleResponseDTO signup(@RequestBody User user) {
        if (userService.createAccount(user)) {
            return SimpleResponseDTO.builder()
                    .success(true)
                    .message("Account created successfully")
                    .build();
        } else {
            return SimpleResponseDTO.builder()
                    .success(false)
                    .message("Failed to create account")
                    .build();
        }
    }

    @PostMapping("/api/change-password")
    public SimpleResponseDTO changePassword(@RequestBody User user) {
        if (userService.changePassword(user)) {
            return SimpleResponseDTO.builder()
                    .success(true)
                    .message("Password changed successfully")
                    .build();
        } else {
            return SimpleResponseDTO.builder()
                    .success(false)
                    .message("Failed to change password")
                    .build();
        }
    }
}
