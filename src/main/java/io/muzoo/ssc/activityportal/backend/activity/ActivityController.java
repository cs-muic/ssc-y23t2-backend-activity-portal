package io.muzoo.ssc.activityportal.backend.activity;

import io.muzoo.ssc.activityportal.backend.SimpleResponseDTO;
import io.muzoo.ssc.activityportal.backend.group.Group;
import io.muzoo.ssc.activityportal.backend.group.GroupRepository;
import io.muzoo.ssc.activityportal.backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

//    @GetMapping("api/{activityId}/activity")
//    public ActivityDTO getActivity(@PathVariable long activityId) {
//return activityRepository.findById(activityId).map(activity -> {
//            return ActivityDTO.builder()
//                    .activity(activity)
//                    .success(true)
//                    .message("Activity found")
//                    .build();
//        }).orElse(ActivityDTO.builder()
//                .success(false)
//                .message("Activity not found")
//                .build());

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

    @PutMapping("api/{groupId}/activity-edit/{activityId}")
    public SimpleResponseDTO editActivity(@RequestBody Activity activityDetail, @PathVariable long groupId, @PathVariable long activityId) {
        return activityService.editActivityDetails(activityDetail, groupId, activityId);
    }
}


