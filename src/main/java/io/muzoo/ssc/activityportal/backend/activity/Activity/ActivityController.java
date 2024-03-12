package io.muzoo.ssc.activityportal.backend.activity.Activity;

import io.muzoo.ssc.activityportal.backend.SimpleResponseDTO;
import io.muzoo.ssc.activityportal.backend.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class ActivityController {

    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private UserRepository userRepository;


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

    /**
     * @param activity Activity object to be created
     * @return SimpleResponseDTO with success/fail and message
     */
    @PostMapping("api/create-activity")
    public SimpleResponseDTO createActivity(@RequestBody Activity activity) {
        System.out.println(activity.getName());
        try {
            activityRepository.save(activity);
            return SimpleResponseDTO.builder().success(true).message("Activity created successfully").build();

        } catch (Exception e) {
            return SimpleResponseDTO.builder().success(false).message("Failed to create activity: " + e.getMessage()).build();
        }
    }

    @GetMapping("api/user-activities")
    public Set<ActivityDTO> getUserActivities() {
        try{
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            boolean loggedIn = principal != null && principal instanceof User;
            if (loggedIn) {
                System.out.println("User is logged in");
                User user = (User) principal;
                io.muzoo.ssc.activityportal.backend.User u = userRepository.findFirstByUsername(user.getUsername());
                Set<Activity> activities = u.getActivities();
                return activities.stream().map(this::mapToDTO).collect(Collectors.toSet());
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


