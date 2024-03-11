package io.muzoo.ssc.activityportal.backend.activity;

import io.muzoo.ssc.activityportal.backend.Activity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ActivityService {
    List<Activity> getAllActivities();

    /**
     * Create an activity
     * @param activity Activity object to be created
     * @return
     */
    Activity createActivity(Activity activity);
}
