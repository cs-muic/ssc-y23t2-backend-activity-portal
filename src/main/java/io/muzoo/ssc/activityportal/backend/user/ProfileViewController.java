package io.muzoo.ssc.activityportal.backend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileViewController {

    @Autowired
    private ProfileViewService profileViewService;

    @GetMapping("/api/user/{username}")
    public UserDTO viewProfile(@PathVariable("username") String username) {
        User u = profileViewService.viewProfile(username);
        if(u != null){
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
}
