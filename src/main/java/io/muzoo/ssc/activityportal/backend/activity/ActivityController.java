package io.muzoo.ssc.activityportal.backend.activity;

import io.muzoo.ssc.activityportal.backend.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ActivityController {
    @Autowired
    private ActivityService activityService;


    @GetMapping("api/get-activity")
    public List<Activity> getActivity() {
        return activityService.getAllActivities();
    }

    @PostMapping("api/create-activity")
    public Activity createActivity(Activity activity) {
        return activityService.createActivity(activity);
    }
}
