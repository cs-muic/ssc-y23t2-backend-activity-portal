package io.muzoo.ssc.activityportal.backend.activityuser;

import io.muzoo.ssc.activityportal.backend.SimpleResponseDTO;
import io.muzoo.ssc.activityportal.backend.activity.*;
import io.muzoo.ssc.activityportal.backend.user.UserRepository;
import io.muzoo.ssc.activityportal.backend.whoami.WhoamiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class ActivityUserController {
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WhoamiService whoamiService;
    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    ActivityService activityService;

    @GetMapping("api/user-activities")
    public Set<ActivityDTO> getUserActivities() {
        activityService.updateActivityStatus();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean loggedIn = principal != null && principal instanceof User;
        if (loggedIn) {
            User user = (User) principal;
            io.muzoo.ssc.activityportal.backend.user.User u = userRepository.findFirstByUsername(user.getUsername());
            return u.getActivities().stream().map(activityMapper::mapToDTO).collect(Collectors.toSet());
        } else {
            return null;
        }
    }
}
