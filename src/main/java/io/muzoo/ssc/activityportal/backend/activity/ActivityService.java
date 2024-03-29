package io.muzoo.ssc.activityportal.backend.activity;

import io.muzoo.ssc.activityportal.backend.SimpleResponseDTO;
import org.springframework.stereotype.Service;


@Service
public interface ActivityService {
    /**
     * Create an activity.
     * @param activityDetail the activity to be created.
     * @param groupId the groupID that the activity belongs to.
     * @return A response DTO informing whether the process was successfull or not.
     *
     */
    SimpleResponseDTO createActivity(Activity activityDetail, long groupId);
    /**
     * Edit an activity.
     *
     * @param activityDetail the activity to be edited.
     * @param groupId the groupID that the activity belongs to.
     * @param activityId the activity ID for the activity to be edited.
     * @return A response DTO informing whether the process was successfull or not.
     */
    SimpleResponseDTO editActivityDetails(Activity activityDetail, long groupId, long activityId);

    /**
     * Delete an activity.
     *
     * @param activityId the activity ID for the activity to be deleted.
     * @return A response DTO informing whether the process was successfull or not.
     */
    SimpleResponseDTO deleteActivity( long activityId);
}

