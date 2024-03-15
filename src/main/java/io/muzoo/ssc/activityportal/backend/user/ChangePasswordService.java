package io.muzoo.ssc.activityportal.backend.user;

import io.muzoo.ssc.activityportal.backend.UserRepository;
import io.muzoo.ssc.activityportal.backend.whoami.WhoamiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ChangePasswordService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private WhoamiService whoamiService;

    /**
     * Change the password of the user
     *
     * @param userPassword the new password
     */
    public boolean changePassword(User userPassword) {
        try {
            User currentUser = whoamiService.getCurrentUser();
            if(currentUser == null) {
                return false;
            }
            currentUser.setPassword(passwordEncoder.encode(userPassword.getPassword()));
            userRepository.save(currentUser);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
