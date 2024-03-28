package io.muzoo.ssc.activityportal.backend.activity;

import io.muzoo.ssc.activityportal.backend.group.Group;
import io.muzoo.ssc.activityportal.backend.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "tbl_activity")

public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "start_time")
    private LocalDateTime start_time;
    @Column(name = "end_time")
    private LocalDateTime end_time;
    @Column(name = "cleanup_date")
    private LocalDateTime cleanup_date;
    @Column(name = "auto_delete_overtime")
    private boolean auto_delete_overtime;
    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "activities", fetch = FetchType.LAZY)
    private Set<User> users;


    // Activity will now have a foreign key called group_id.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;
}
