package io.muzoo.ssc.activityportal.backend.activityuser;

import io.muzoo.ssc.activityportal.backend.activity.ActivityDTO;
import io.muzoo.ssc.activityportal.backend.activity.ActivityMapper;
import io.muzoo.ssc.activityportal.backend.activity.ActivityService;
import io.muzoo.ssc.activityportal.backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class ActivityUserController {
    @Autowired
    ActivityService activityService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ActivityMapper activityMapper;

    @GetMapping("api/user-activities")
    public Set<ActivityDTO> getUserActivities() {
        activityService.updateAndDeleteActivityStatus();
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
