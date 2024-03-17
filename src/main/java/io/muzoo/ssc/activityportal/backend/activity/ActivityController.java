package io.muzoo.ssc.activityportal.backend.activity;

import io.muzoo.ssc.activityportal.backend.SimpleResponseDTO;
import io.muzoo.ssc.activityportal.backend.group.Group;
import io.muzoo.ssc.activityportal.backend.group.GroupRepository;
import io.muzoo.ssc.activityportal.backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActivityController {

    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private ActivityMapper activityMapper;

    /**
     * Get the activity information by its id.
     * @param activity_id the id of the activity.
     * @return the activity information in DTO form.
     */
    @GetMapping("api/{activity_id}/activity")
    public ActivityDTO getActivity(@PathVariable long activity_id) {
        return activityRepository.findById(activity_id).map(activityMapper::mapToDTO).orElse(null);
    }

    /**
     * @param activity Activity object to be created
     * @return SimpleResponseDTO with success/fail and message
     */
    @PostMapping("api/{groupId}/activity-create")
    public SimpleResponseDTO createActivity(@RequestBody Activity activity, @PathVariable long groupId) {
        return groupRepository.findById(groupId).map(group -> {
            activity.setGroup(group);
            activityRepository.save(activity);
            return SimpleResponseDTO.builder().success(true).message("Activity created").build();
        }).orElse(SimpleResponseDTO.builder().success(false).message("Group not found").build());
    }

    /**
     * Edit the activity details.
     * @param activityDetail the activity details to be edited.
     * @param groupId the group id that the activity should belong to.
     * @param activityId the activity id that is going to be edited.
     * @return SimpleResponseDTO with success/fail and message.
     */
    @PutMapping("api/{groupId}/activity-edit/{activityId}")
    public SimpleResponseDTO editActivity(@RequestBody Activity activityDetail, @PathVariable long groupId, @PathVariable long activityId) {
        return activityService.editActivityDetails(activityDetail, groupId, activityId);
    }

    @PostMapping("api/{groupId}/activity-edit")
    public SimpleResponseDTO editActivity(@RequestBody Activity activity, @PathVariable long groupId) {

        return groupRepository.findById(groupId).map(group -> {
            activity.setGroup(group);
            activityRepository.save(activity);
            return SimpleResponseDTO.builder().success(true).message("Activity edited").build();
        }).orElse(SimpleResponseDTO.builder().success(false).message("Group not found").build());
    }
}


