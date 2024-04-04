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

    /**
     * View the profile of a user.
     *
     * @param username (String) : The username of the user trying to view the profile.
     * @return true if succcessful, false if unsuccessful.
     */

    public User viewProfile(String username) {
        return userRepository.findFirstByUsername(username);
    }

    /**
     * Edit the profile of currently logged-in user.
     *
     * @param user (User) : The profile of the user trying to edit.
     * @return true if succcessful, false if unsuccessful.
     */

    public boolean editProfile(User user) {
        try {
            User currentUser = whoamiService.getCurrentUser();
            if (currentUser == null) {
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

    /**
     * Create an account for the user.
     *
     * @param user (User) : The user trying to create an account.
     * @return true if succcessful, false if unsuccessful.
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

    /**
     * Change the password of currently logged-in user.
     *
     * @param userPassword (User) : The user trying to change the password.
     * @return true if succcessful, false if unsuccessful.
     */

    public boolean changePassword(User userPassword) {
        try {
            User currentUser = whoamiService.getCurrentUser();
            if (currentUser == null) {
                return false;
            }
            currentUser.setPassword(passwordEncoder.encode(userPassword.getPassword()));
            userRepository.save(currentUser);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Fetch the user based on the userID
     * @param userID (long) : The id of the user. 
     * @return (User) if successful, null if unsuccessful.
     */
    public User fetchUserByID(long userID){
        return userRepository.findById(userID).orElse(null);
    }


}
