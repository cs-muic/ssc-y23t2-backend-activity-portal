package io.muzoo.ssc.activityportal.backend.whoami;

import io.muzoo.ssc.activityportal.backend.user.User;
import io.muzoo.ssc.activityportal.backend.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class WhoamiService {
    @Autowired
    private UserRepository userRepository;

    /**
     * Get the current user for backend use
     * @return the user if logged in. Null if not
     */
    public User getCurrentUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null && principal instanceof org.springframework.security.core.userdetails.User) {
            org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) principal;
            return userRepository.findFirstByUsername(user.getUsername());
        } else return null;
    }
}
