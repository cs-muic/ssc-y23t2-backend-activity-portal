package io.muzoo.ssc.activityportal.backend.activitygroup;

import io.muzoo.ssc.activityportal.backend.activity.Activity;
import io.muzoo.ssc.activityportal.backend.activity.ActivityDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

public interface ActivityGroupService {
    /**
     * Gets the list of activities that a group has.
     *
     * @param groupId the group id the is parsed in
     * @return the list of activities that the group has
     */
    Set<Activity> getGroupActivity(long groupId);
}
