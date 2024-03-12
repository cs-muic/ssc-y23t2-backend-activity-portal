package io.muzoo.ssc.activityportal.backend.user;

import io.muzoo.ssc.activityportal.backend.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileViewController {

    @Autowired
    private ProfileViewService profileViewService;

    @GetMapping("/api/user/{username}")
    public User viewProfile(@PathVariable("username") String username) {
        return profileViewService.viewProfile(username);
    }
}
