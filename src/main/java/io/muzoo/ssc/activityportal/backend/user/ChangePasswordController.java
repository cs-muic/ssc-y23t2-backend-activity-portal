package io.muzoo.ssc.activityportal.backend.user;


import io.muzoo.ssc.activityportal.backend.SimpleResponseDTO;
import io.muzoo.ssc.activityportal.backend.User;
import io.muzoo.ssc.activityportal.backend.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChangePasswordController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/api/change-password")
    public SimpleResponseDTO editProfile(@RequestBody User user) {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User currentUser = userRepository.findFirstByUsername(((UserDetails) principal).getUsername());
            currentUser.setPassword(passwordEncoder.encode(user.getPassword()));
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
