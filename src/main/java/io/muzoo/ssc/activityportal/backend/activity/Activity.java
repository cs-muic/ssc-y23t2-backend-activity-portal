package io.muzoo.ssc.activityportal.backend.activity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.muzoo.ssc.activityportal.backend.group.Group;
import io.muzoo.ssc.activityportal.backend.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
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
    @JsonFormat(pattern = "M/d/yyyy, h:mm:ss a")
    private Timestamp start_time;
    @Column(name = "end_time")
    @JsonFormat(pattern = "M/d/yyyy, h:mm:ss a")
    private Timestamp end_time;
    @Column(name = "auto_delete_overtime")
    private boolean auto_delete_overtime;
    @Column(name = "description")
    private String description;
    @Column(name = "status")
    private String status;

    @JsonIgnore
    @ManyToMany(mappedBy = "activities", fetch = FetchType.LAZY)
    private Set<User> users;


    @JsonIgnore
    // Activity will now have a foreign key called group_id.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;
}
