package io.muzoo.ssc.activityportal.backend.activity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Setter
@Getter

public class ActivityDTO {
    private long id;
    private String name;
    private Timestamp start_time;
    private Timestamp end_time;
    private boolean auto_delete_overtime;
    private String description;
    private String status;
}
