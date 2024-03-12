package io.muzoo.ssc.activityportal.backend.activity.Activity;

import java.time.LocalDateTime;

public class ActivityDTO {
    private long id;
    private String name;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private LocalDateTime cleanup_date;
    private boolean auto_delete_overtime;
    private String description;

    // getters and setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getStart_time() {
        return start_time;
    }

    public void setStart_time(LocalDateTime start_time) {
        this.start_time = start_time;
    }

    public LocalDateTime getEnd_time() {
        return end_time;
    }

    public void setEnd_time(LocalDateTime end_time) {
        this.end_time = end_time;
    }

    public LocalDateTime getCleanup_date() {
        return cleanup_date;
    }

    public void setCleanup_date(LocalDateTime cleanup_date) {
        this.cleanup_date = cleanup_date;
    }

    public boolean isAuto_delete_overtime() {
        return auto_delete_overtime;
    }

    public void setAuto_delete_overtime(boolean auto_delete_overtime) {
        this.auto_delete_overtime = auto_delete_overtime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
