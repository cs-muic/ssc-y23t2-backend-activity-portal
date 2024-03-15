package io.muzoo.ssc.activityportal.backend.whoami;

import io.muzoo.ssc.activityportal.backend.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller to retrieve currently logged-in user.
 */
@RestController
public class WhoamiController {
    @Autowired
    private WhoamiService whoamiService;

    /**
     * Get the info of the logged in user
     * @return
     */
    @GetMapping("/api/whoami")
    public WhoamiDTO whoami(){
        try {
            User u = whoamiService.getCurrentUser();
            if (u != null){
                //is logged in
                return WhoamiDTO.builder()
                        .isLoggedIn(true)
                        .displayName(u.getDisplayName())
                        .role(u.getRole())
                        .username(u.getUsername())
                        .bio(u.getBio())
                        .userID(u.getId())
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
