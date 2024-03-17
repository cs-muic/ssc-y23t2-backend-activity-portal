package io.muzoo.ssc.activityportal.backend.activitygroup;

import io.muzoo.ssc.activityportal.backend.activity.Activity;
import io.muzoo.ssc.activityportal.backend.activity.ActivityDTO;
import io.muzoo.ssc.activityportal.backend.activity.ActivityRepository;
import io.muzoo.ssc.activityportal.backend.group.GroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class ActivityGroupController {
    private static final Logger logger = LoggerFactory.getLogger(ActivityGroupController.class);
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private ActivityGroupService activityGroupService;

    /**
     * Gets the list of activities that a group has.
     *
     * @param groupId the group id the is parsed in
     * @return the list of activities that the group has
     */
    @GetMapping("api/group-activity/{groupId}")
    public GroupActivityResponseDTO getUserActivities(@PathVariable long groupId) {
        System.out.println(groupId);
        GroupActivityResponseDTO response = new GroupActivityResponseDTO();
        Set<ActivityDTO> activities = activityGroupService.getGroupActivity(groupId).stream().map(this::mapToDTO).collect(Collectors.toSet());
        if (activities.isEmpty()){
            response.setSuccess(false);
            response.setMessage("No activities found");
            return response;
        }
        response.setSuccess(true);
        response.setMessage("Successfully retrieved group activities");
        response.setActivities(activities);
        return response;
    }

    private ActivityDTO mapToDTO(Activity activity) {
        ActivityDTO dto = new ActivityDTO();
        dto.setId(activity.getId());
        dto.setName(activity.getName());
        dto.setStart_time(activity.getStart_time());
        dto.setEnd_time(activity.getEnd_time());
        dto.setCleanup_date(activity.getCleanup_date());
        dto.setAuto_delete_overtime(activity.isAuto_delete_overtime());
        dto.setDescription(activity.getDescription());
        return dto;
    }

}

