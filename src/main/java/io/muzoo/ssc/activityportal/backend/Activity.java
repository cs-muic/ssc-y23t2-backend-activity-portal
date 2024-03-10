package io.muzoo.ssc.activityportal.backend;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "tbl_activity")

public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private LocalDateTime start_time;

    private LocalDateTime end_time;

    private LocalDateTime cleanup_date;

    private boolean auto_delete_overtime;

    private String description;
}
