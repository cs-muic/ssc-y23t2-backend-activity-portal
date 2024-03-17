package io.muzoo.ssc.activityportal.backend.activitygroup;

import io.muzoo.ssc.activityportal.backend.activity.ActivityDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class GroupActivityResponseDTO {
    private boolean success;
    private String message;
    private Set<ActivityDTO> activities;
}

