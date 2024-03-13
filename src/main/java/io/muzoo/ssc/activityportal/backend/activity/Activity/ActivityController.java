package io.muzoo.ssc.activityportal.backend.activity.Activity;

import io.muzoo.ssc.activityportal.backend.SimpleResponseDTO;
import io.muzoo.ssc.activityportal.backend.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
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

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean loggedIn = principal != null && principal instanceof User;
        if (loggedIn) {
            User user = (User) principal;
            io.muzoo.ssc.activityportal.backend.User u = userRepository.findFirstByUsername(user.getUsername());
            return u.getActivities().stream().map(this::mapToDTO).collect(Collectors.toSet());
        } else {
            return null;
        }
    }
    @PostMapping("api/unjoin-activity/{activityId}")
    public SimpleResponseDTO unjoinActivity(@PathVariable long activityId) {
        try {
            // Check if user is authenticated
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            boolean loggedIn = principal != null && principal instanceof User;
            if (loggedIn) {
                User user = (User) principal;
                io.muzoo.ssc.activityportal.backend.User u = userRepository.findFirstByUsername(user.getUsername());
                Activity activity = activityRepository.findFirstById(activityId);
                u.getActivities().remove(activity);
                userRepository.save(u);
                return SimpleResponseDTO.builder().success(true).message("Activity unjoined successfully").build();
            } else {
                return SimpleResponseDTO.builder().success(false).message("Failed to unjoin activity").build();
            }
        } catch (Exception e) {
            return SimpleResponseDTO.builder().success(false).message("Failed to unjoin activity: " + e.getMessage()).build();
        }
    }
}


