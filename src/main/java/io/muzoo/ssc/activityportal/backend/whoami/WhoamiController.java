package io.muzoo.ssc.activityportal.backend.whoami;

import io.muzoo.ssc.activityportal.backend.User;
import io.muzoo.ssc.activityportal.backend.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller to retrieve currently logged-in user.
 */
@RestController
public class WhoamiController {
    @Autowired
    private UserRepository userRepository;

    /**
     * Make sure that all API path begins with /api.
     */
    @GetMapping("/api/whoami")
    public WhoamiDTO whoami(){
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal != null && principal instanceof org.springframework.security.core.userdetails.User){
                //is logged in
                org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) principal;
                User u = userRepository.findFirstByUsername(user.getUsername());
                return WhoamiDTO.builder()
                        .loggedIn(true)
                        .displayName(u.getDisplayName())
                        .role(u.getRole())
                        .username(u.getUsername())
                        .build();
            } else {
                //is not logged in
                return WhoamiDTO.builder()
                        .loggedIn(false)
                        .build();
            }
        } catch (Exception e) {
            return WhoamiDTO.builder()
                    .loggedIn(false)
                    .build();
        }
    }
}
