package io.muzoo.ssc.activityportal.backend.user;

import io.muzoo.ssc.activityportal.backend.User;
import io.muzoo.ssc.activityportal.backend.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class EditProfileService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Edit the profile of the user
     *
     * @return status on whether the profile is edited or not
     */
    public boolean editProfile(User user) {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User currentUser = userRepository.findFirstByUsername(((UserDetails) principal).getUsername());
            currentUser.setDisplayName(user.getDisplayName());
            userRepository.save(currentUser);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
