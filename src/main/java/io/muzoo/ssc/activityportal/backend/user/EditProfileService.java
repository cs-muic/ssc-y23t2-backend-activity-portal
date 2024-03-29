package io.muzoo.ssc.activityportal.backend.user;

import io.muzoo.ssc.activityportal.backend.whoami.WhoamiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditProfileService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WhoamiService whoamiService;

    /**
     * Edit the profile of the user
     *
     * @return status on whether the profile is edited or not
     */
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
}
