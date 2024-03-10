package io.muzoo.ssc.activityportal.backend.activity;

import io.muzoo.ssc.activityportal.backend.Activity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ActivityService {
    List<Activity> getAllActivities();
    Activity createActivity(Activity activity);
}
