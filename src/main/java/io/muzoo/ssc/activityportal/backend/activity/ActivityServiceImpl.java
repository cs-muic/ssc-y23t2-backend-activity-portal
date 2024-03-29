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

    @Override
    public SimpleResponseDTO createActivity(Activity activity, long groupId) {
        User u = whoamiService.getCurrentUser();
        if (u == null) {
            return SimpleResponseDTO.builder().success(false).message("User is not logged in").build();
        }
        Group group = groupRepository.findFirstById(groupId);
        if (group == null) {
            return SimpleResponseDTO.builder().success(false).message("Group not found").build();
        }
        if (group.getOwnerID() != u.getId()) {
            return SimpleResponseDTO.builder().success(false).message("User is not the owner of the group").build();
        }
        activity.setGroup(group);
        activityRepository.save(activity);
        group.getActivities().add(activity);
        groupRepository.save(group);
        // Add the activity to all the users in the group
        for (User user: group.getUsers()){
            user.getActivities().add(activity);
        }

        return SimpleResponseDTO.builder().success(true).message("Activity created").build();
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

    @Transactional
    public SimpleResponseDTO deleteActivity( long activityId) {
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