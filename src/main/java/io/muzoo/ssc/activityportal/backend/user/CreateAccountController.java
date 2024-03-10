package io.muzoo.ssc.activityportal.backend.user;

import io.muzoo.ssc.activityportal.backend.SimpleResponseDTO;
import io.muzoo.ssc.activityportal.backend.User;
import io.muzoo.ssc.activityportal.backend.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateAccountController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Create new account
     * @return status on whether the account is created or not
     */

    @PostMapping("/api/create-account")
    public SimpleResponseDTO signup(@RequestBody User user) {
        if (userRepository.findFirstByUsername(user.getUsername()) != null) {
            return SimpleResponseDTO.builder()
                    .success(false)
                    .message("Username already exists")
                    .build();
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        userRepository.save(user);

        return SimpleResponseDTO.builder()
                .success(true)
                .message("Account created successfully")
                .build();
    }
}
