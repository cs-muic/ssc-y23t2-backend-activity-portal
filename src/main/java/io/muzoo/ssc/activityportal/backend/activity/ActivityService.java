package io.muzoo.ssc.activityportal.backend.activity;
<<<<<<< HEAD
import io.muzoo.ssc.activityportal.backend.SimpleResponseDTO;
=======
>>>>>>> 2819bd4 (feat: Created a many to one relationship between group and activity, create activity is now joined with group system. Group can now see a list of activities they have)
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ActivityService {
    /**
     * Edit an activity.
     *
         * @param activityDetail the activity to be edited.
     * @param groupId the groupID that the activity belongs to.
     * @param activityId the activity ID for the activity to be edited.
     * @return A response DTO informing whether the process was successfull or not.
     */
    SimpleResponseDTO editActivityDetails(Activity activityDetail, long groupId, long activityId);
}
