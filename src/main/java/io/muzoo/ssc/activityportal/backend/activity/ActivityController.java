package io.muzoo.ssc.activityportal.backend.activity;

import io.muzoo.ssc.activityportal.backend.SimpleResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ActivityController {

    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private ActivityMapper activityMapper;

    /**
     * Get the activity information by its id.
     *
     * @param activity_id the id of the activity.
     * @return the activity information in DTO form.
     */
    @GetMapping("api/{activity_id}/activity")
    public ActivityDTO getActivity(@PathVariable long activity_id) {
        activityService.updateActivityStatus();
        return activityRepository.findById(activity_id).map(activityMapper::mapToDTO).orElse(null);
    }

    /**
     * @param activity Activity object to be created
     * @return SimpleResponseDTO with success/fail and message
     */
    @PostMapping("api/{groupId}/activity-create")
    public SimpleResponseDTO createActivity(@RequestBody Activity activity, @PathVariable long groupId) {
        return activityService.createActivity(activity, groupId);
    }

    /**
     * Edit the activity details.
     *
     * @param activityDetail the activity details to be edited.
     * @param groupId        the group id that the activity should belong to.
     * @param activityId     the activity id that is going to be edited.
     * @return SimpleResponseDTO with success/fail and message.
     */
    @PutMapping("api/{groupId}/activity-edit/{activityId}")
    public SimpleResponseDTO editActivity(@RequestBody Activity activityDetail, @PathVariable long groupId, @PathVariable long activityId) {
        return activityService.editActivityDetails(activityDetail, groupId, activityId);
    }

    /**
     * Delete the activity by its id.
     *
     * @param activity_id the id of the activity.
     * @return SimpleResponseDTO with success/fail and message.
     */
    @DeleteMapping("api/{activity_id}/activity-delete")
    public SimpleResponseDTO deleteActivity(@PathVariable long activity_id) {
        return activityService.deleteActivity(activity_id);
    }
}


