package io.muzoo.ssc.activityportal.backend.activity.Activity;

import io.muzoo.ssc.activityportal.backend.SimpleResponseDTO;
import io.muzoo.ssc.activityportal.backend.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@RestController
public class ActivityController {

    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private UserRepository userRepository;


    /**
     * @param activity Activity object to be created
     * @return SimpleResponseDTO with success/fail and message
     */
    @PostMapping("api/create-activity")
    public SimpleResponseDTO createActivity(@RequestBody Activity activity) {
        System.out.println(activity.getName());
        try {
            activityRepository.save(activity);
            return SimpleResponseDTO.builder().success(true).message("Activity created successfully").build();

        } catch (Exception e) {
            return SimpleResponseDTO.builder().success(false).message("Failed to create activity: " + e.getMessage()).build();
        }
    }
}


