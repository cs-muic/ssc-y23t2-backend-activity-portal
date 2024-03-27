package io.muzoo.ssc.activityportal.backend.activity;

import io.muzoo.ssc.activityportal.backend.SimpleResponseDTO;
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
    private GroupRepository groupRepository;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private UserRepository userRepository;


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
}


