package io.muzoo.ssc.activityportal.backend.auth;

import io.muzoo.ssc.activityportal.backend.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * The service that will be made to check the user details
 */
@Service
public class OurUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    /**
     * loads the detail of the user for authentication
     * @param username the username to search the user's information
     * @return the information of that user
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        io.muzoo.ssc.activityportal.backend.user.User u = userRepository.findFirstByUsername(username);
        if (u != null) {
            return User.withUsername(u.getUsername())
				.password(u.getPassword())
				.roles(u.getRole())
				.build();
        } else {
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }
}
