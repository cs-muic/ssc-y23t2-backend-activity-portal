package io.muzoo.ssc.activityportal.backend.user;

import io.muzoo.ssc.activityportal.backend.User;
import io.muzoo.ssc.activityportal.backend.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateAccountService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Create new account
     *
     * @return status on whether the account is created or not
     */
    public boolean createAccount(User user) {
        try {
            if (userRepository.findFirstByUsername(user.getUsername()) != null) {
                return false;
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole("USER");
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
