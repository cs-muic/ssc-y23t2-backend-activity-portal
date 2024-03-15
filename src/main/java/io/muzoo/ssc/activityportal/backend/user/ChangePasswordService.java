package io.muzoo.ssc.activityportal.backend.user;

import io.muzoo.ssc.activityportal.backend.User;
import io.muzoo.ssc.activityportal.backend.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ChangePasswordService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Change the password of the user
     *
     * @param userPassword the new password
     */
    public boolean changePassword(User userPassword) {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User currentUser = userRepository.findFirstByUsername(((UserDetails) principal).getUsername());
            currentUser.setPassword(passwordEncoder.encode(userPassword.getPassword()));
            userRepository.save(currentUser);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
