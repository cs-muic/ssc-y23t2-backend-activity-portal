package io.muzoo.ssc.activityportal.backend.auth;

import io.muzoo.ssc.activityportal.backend.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class OurUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        io.muzoo.ssc.activityportal.backend.User u = userRepository.findFirstByUsername(username);
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
