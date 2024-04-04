package io.muzoo.ssc.activityportal.backend.activity;

import org.springframework.stereotype.Component;

@Component
public class ActivityMapper {

    public ActivityDTO mapToDTO(Activity activity) {
        ActivityDTO dto = new ActivityDTO();
        dto.setId(activity.getId());
        dto.setName(activity.getName());
        dto.setStart_time(activity.getStart_time());
        dto.setEnd_time(activity.getEnd_time());
        dto.setAuto_delete_overtime(activity.isAuto_delete_overtime());
        dto.setDescription(activity.getDescription());
        dto.setStatus(activity.getStatus());
        return dto;
    }
}