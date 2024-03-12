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
     * Get the info of the logged in user
     * @return
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
                        .isLoggedIn(true)
                        .displayName(u.getDisplayName())
                        .role(u.getRole())
                        .username(u.getUsername())
                        .bio(u.getBio())
                        .build();
            } else {
                //is not logged in
                return WhoamiDTO.builder()
                        .isLoggedIn(false)
                        .build();
            }
        } catch (Exception e) {
            return WhoamiDTO.builder()
                    .isLoggedIn(false)
                    .build();
        }
    }
}
