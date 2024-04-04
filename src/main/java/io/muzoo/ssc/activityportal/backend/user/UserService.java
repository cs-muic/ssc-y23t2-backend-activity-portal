package io.muzoo.ssc.activityportal.backend.user;


import io.muzoo.ssc.activityportal.backend.whoami.WhoamiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WhoamiService whoamiService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User viewProfile(String username) {
        return userRepository.findFirstByUsername(username);
    }

    public boolean editProfile(User user) {
        try {
            User currentUser = whoamiService.getCurrentUser();
            if(currentUser == null) {
                return false;
            }
            currentUser.setDisplayName(user.getDisplayName());
            currentUser.setBio(user.getBio());
            userRepository.save(currentUser);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

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
