package io.muzoo.ssc.activityportal.backend.activity;

import io.muzoo.ssc.activityportal.backend.Activity;
import io.muzoo.ssc.activityportal.backend.ActivityRepository;
import io.muzoo.ssc.activityportal.backend.SimpleResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
TODO:
 1. End time can't be before start time
 2. Time clean up time and delete activity after cleanup time has been reached
 3. Edit activity
 4. Delete activity
**/

@RestController
public class ActivityController {

    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private ActivityService activityService;

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
