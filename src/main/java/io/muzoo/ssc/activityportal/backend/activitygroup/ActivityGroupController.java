package io.muzoo.ssc.activityportal.backend.activitygroup;

import io.muzoo.ssc.activityportal.backend.activity.Activity;
import io.muzoo.ssc.activityportal.backend.activity.ActivityDTO;
import io.muzoo.ssc.activityportal.backend.activity.ActivityMapper;
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
    @Autowired
    private ActivityMapper activityMapper;


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
        Set<ActivityDTO> activities = activityGroupService.getGroupActivity(groupId).stream().map(activityMapper::mapToDTO).collect(Collectors.toSet());
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
}

