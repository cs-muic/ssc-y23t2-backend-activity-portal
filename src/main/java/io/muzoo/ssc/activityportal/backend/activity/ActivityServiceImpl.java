package io.muzoo.ssc.activityportal.backend.activity;

import io.muzoo.ssc.activityportal.backend.Activity;
import io.muzoo.ssc.activityportal.backend.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {
    private ActivityRepository activityRepository;

    @Autowired
    public ActivityServiceImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    /**
     * Lists all the activities in the database
     * @return a list of all the activities
     */
    @Override
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    /**
     * Creates a new activity in the database
     * @param activity the activity to be created
     * @return the created activity
     */
    @Override
    public Activity createActivity(Activity activity) {
        return activityRepository.save(activity);
    }
}

