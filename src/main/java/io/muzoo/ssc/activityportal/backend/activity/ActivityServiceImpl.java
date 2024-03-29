package io.muzoo.ssc.activityportal.backend.activity;

import io.muzoo.ssc.activityportal.backend.SimpleResponseDTO;
import io.muzoo.ssc.activityportal.backend.group.Group;
import io.muzoo.ssc.activityportal.backend.group.GroupRepository;
import io.muzoo.ssc.activityportal.backend.user.User;
import io.muzoo.ssc.activityportal.backend.whoami.WhoamiService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private WhoamiService whoamiService;

    @Autowired
    public ActivityServiceImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public SimpleResponseDTO editActivityDetails(Activity activityDetail, long groupId, long activityId) {
        User u = whoamiService.getCurrentUser();
        if (u == null) {
            return SimpleResponseDTO.builder().success(false).message("User is not logged in").build();
        }
        Group group = groupRepository.findFirstById(groupId);
        // Check if the group exists
        if (group == null) {
            return SimpleResponseDTO.builder().success(false).message("Group not found").build();
        }
        Activity updateActivity = activityRepository.findFirstById(activityId);
        // Check if the user is the owner of the group
        if (group.getOwnerID() != u.getId()) {
            return SimpleResponseDTO.builder().success(false).message("User is not the owner of the group").build();
        }
        // Check if the activity exists
        if (updateActivity == null) {
            return SimpleResponseDTO.builder().success(false).message("Activity not found").build();
        }
        // Check if the activity belongs to the group
        if (updateActivity.getGroup().getId() != groupId) {
            return SimpleResponseDTO.builder().success(false).message("Activity does not belong to the group").build();
        }
        updateActivity.setDescription(activityDetail.getDescription());
        updateActivity.setStart_time(activityDetail.getStart_time());
        updateActivity.setEnd_time(activityDetail.getEnd_time());

        activityRepository.save(updateActivity);
        return SimpleResponseDTO.builder().success(true).message("Activity edited").build();
    }


    /** Make this transactional so that it must complete every step in the method or rollback
    1. Retrieve the Activity by its ID.
    2. Check if the Activity exists.
    3. Retrieve the Group from the Activity.
    4. Check if the current user is the owner of the Group.
    5. Retrieve the User entities associated with the Activity.
    6. For each User, remove the Activity from the user's activities collection and save the User.
    7. Remove the Activity from the Group's activities collection and save the Group.
    8. Delete the Activity.**/
    @Transactional
    public SimpleResponseDTO deleteActivity( long activityId) {
        // TODO: delete an Activity from a Group and also remove the entries that associate all User and the Activity that is being deleted, you can follow these steps:
        Activity activity = activityRepository.findFirstById(activityId);
        if (activity == null) {
            return SimpleResponseDTO.builder().success(false).message("Activity not found").build();
        }
        Group group = activity.getGroup();
        User u = whoamiService.getCurrentUser();
        if (group.getOwnerID() != u.getId()) {
            return SimpleResponseDTO.builder().success(false).message("User is not the owner of the group").build();
        }
        for (User user: activity.getUsers()){
            user.getActivities().remove(activity);
        }
        group.getActivities().remove(activity);
        activityRepository.delete(activity);
        return SimpleResponseDTO.builder().success(true).message("Activity deleted").build();
    }
}