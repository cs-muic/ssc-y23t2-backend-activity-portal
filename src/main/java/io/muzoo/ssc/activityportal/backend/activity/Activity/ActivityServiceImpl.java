package io.muzoo.ssc.activityportal.backend.activity.Activity;

import io.muzoo.ssc.activityportal.backend.activity.Activity.Activity;
import io.muzoo.ssc.activityportal.backend.activity.Activity.ActivityRepository;
import io.muzoo.ssc.activityportal.backend.activity.Activity.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {
    private ActivityRepository activityRepository;


    @Autowired
    public ActivityServiceImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

}


