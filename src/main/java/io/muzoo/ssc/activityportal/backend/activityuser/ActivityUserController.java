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
    ActivityUserService activityUserService;

    @GetMapping("api/user-activities")
    public Set<ActivityDTO> getUserActivities() {
        return activityUserService.getUserActivities();
    }
}
