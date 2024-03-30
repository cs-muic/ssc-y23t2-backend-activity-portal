package io.muzoo.ssc.activityportal.backend.activity;

import io.muzoo.ssc.activityportal.backend.SimpleResponseDTO;
import io.muzoo.ssc.activityportal.backend.group.Group;
import io.muzoo.ssc.activityportal.backend.group.GroupRepository;
import io.muzoo.ssc.activityportal.backend.user.User;
import io.muzoo.ssc.activityportal.backend.user.UserRepository;
import io.muzoo.ssc.activityportal.backend.whoami.WhoamiService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WhoamiService whoamiService;

    @Autowired
    public ActivityServiceImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public SimpleResponseDTO checkUserAndGroup(long groupId) {
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
        return SimpleResponseDTO.builder().success(true).message("User and group checked").build();
    }

    @Scheduled(fixedRate = 60000) // Updates every minute
    public void updateActivityStatus() {
        LocalDateTime now = LocalDateTime.now();
        // Get all activities
        Iterable<Activity> activities = activityRepository.findAll();
        for (Activity activity : activities) {
            if (now.isBefore(activity.getStart_time())) {
                activity.setStatus("PENDING");
            } else if (now.isAfter(activity.getStart_time()) && now.isBefore(activity.getEnd_time())) {
                activity.setStatus("ONGOING");
            } else if (now.isAfter(activity.getEnd_time())) {
                activity.setStatus("COMPLETED");
            }
        }
    }
    @Override
    @Transactional
    public SimpleResponseDTO createActivity(Activity activity, long groupId) {
        SimpleResponseDTO checkResult = checkUserAndGroup(groupId);
        if (!checkResult.isSuccess()) {
            return checkResult;
        }
        return groupRepository.findById(groupId).map(group -> {
            activity.setGroup(group);
            activityRepository.save(activity);
            List<User> members = group.getUsers();
            members.forEach(member -> {
                member.getActivities().add(activity);
                userRepository.save(member);
            });
            // Update the status of the activity
            updateActivityStatus();
            return SimpleResponseDTO.builder().success(true).message("Activity created").build();
        }).orElse(SimpleResponseDTO.builder().success(false).message("Group not found").build());
    }

    public SimpleResponseDTO editActivityDetails(Activity activityDetail, long groupId, long activityId) {
        SimpleResponseDTO checkResult = checkUserAndGroup(groupId);
        if (!checkResult.isSuccess()) {
            return checkResult;
        }
        Activity updateActivity = activityRepository.findFirstById(activityId);
        // Check if the activity exists
        if (updateActivity == null) {
            return SimpleResponseDTO.builder().success(false).message("Activity not found").build();
        }
        // Check if the activity belongs to the group
        if (updateActivity.getGroup().getId() != groupId) {
            return SimpleResponseDTO.builder().success(false).message("Activity does not belong to the group").build();
        }
        updateActivityStatus();
        updateActivity.setName(activityDetail.getName());
        updateActivity.setDescription(activityDetail.getDescription());
        updateActivity.setStart_time(activityDetail.getStart_time());
        updateActivity.setEnd_time(activityDetail.getEnd_time());

        activityRepository.save(updateActivity);
        return SimpleResponseDTO.builder().success(true).message("Activity edited").build();
    }

    @Transactional
    public SimpleResponseDTO deleteActivity(long activityId) {
        Activity activity = activityRepository.findFirstById(activityId);
        Group group = activity.getGroup();
        long groupId = group.getId();
        SimpleResponseDTO checkResult = checkUserAndGroup(groupId);
        if (!checkResult.isSuccess()) {
            return checkResult;
        }
        for (User user : activity.getUsers()) {
            user.getActivities().remove(activity);
        }
        updateActivityStatus();
        group.getActivities().remove(activity);
        activityRepository.delete(activity);
        return SimpleResponseDTO.builder().success(true).message("Activity deleted").build();
    }

}