package io.muzoo.ssc.activityportal.backend.user;


import io.muzoo.ssc.activityportal.backend.SimpleResponseDTO;
import io.muzoo.ssc.activityportal.backend.User;
import io.muzoo.ssc.activityportal.backend.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EditProfileController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/api/edit-profile")
    public SimpleResponseDTO editProfile(@RequestBody User user) {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User currentUser = userRepository.findFirstByUsername(((UserDetails) principal).getUsername());
            currentUser.setDisplayName(user.getDisplayName());
            userRepository.save(currentUser);
            return SimpleResponseDTO.builder()
                    .success(true)
                    .message("Profile edited successfully")
                    .build();
        } catch (Exception e) {
            return SimpleResponseDTO.builder()
                    .success(false)
                    .message("Failed to edit profile: " + e.getMessage())
                    .build();
        }
    }
}
