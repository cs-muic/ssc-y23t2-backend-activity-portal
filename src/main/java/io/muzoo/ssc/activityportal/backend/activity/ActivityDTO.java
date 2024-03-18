package io.muzoo.ssc.activityportal.backend.activity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
public class ActivityDTO {
    private long id;
    private String name;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private LocalDateTime cleanup_date;
    private boolean auto_delete_overtime;
    private String description;
}
