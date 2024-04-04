package io.muzoo.ssc.activityportal.backend.activityuser;

import io.muzoo.ssc.activityportal.backend.activity.ActivityDTO;

import java.util.Set;

public interface ActivityUserService {
    Set<ActivityDTO> getUserActivities();

}
