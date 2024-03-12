package io.muzoo.ssc.activityportal.backend.activity;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "tbl_user_activity")
public class UserActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private long userID;
    private long activityID;

}
