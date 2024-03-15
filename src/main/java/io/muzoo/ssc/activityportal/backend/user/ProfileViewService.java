package io.muzoo.ssc.activityportal.backend.user;

import io.muzoo.ssc.activityportal.backend.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileViewService {

    @Autowired
    private UserRepository userRepository;

    public User viewProfile(String username) {
        return userRepository.findFirstByUsername(username);
    }
}
