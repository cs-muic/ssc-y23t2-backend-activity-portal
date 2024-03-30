package io.muzoo.ssc.activityportal.backend.activitygroup;

import io.muzoo.ssc.activityportal.backend.activity.*;
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
    private ActivityGroupResponseDTO activityGroupResponseDTO;
    @Autowired
   private ActivityMapper activityMapper;
    @Autowired
    private ActivityService activityService;
    /**
     * Gets the list of activities that a group has.
     *
     * @param groupId the group id the is parsed in
     * @return the list of activities that the group has
     */
    @GetMapping("api/group-activity/{groupId}")
    public ActivityGroupResponseDTO  getGroupActivities(@PathVariable long groupId) {
        activityService.updateActivityStatus();
        System.out.println(groupId);
        ActivityGroupResponseDTO response = new ActivityGroupResponseDTO();
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
