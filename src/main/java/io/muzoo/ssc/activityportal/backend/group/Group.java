package io.muzoo.ssc.activityportal.backend.group;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tbl_group")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int maxMember;
    private int memberCount;
    private long ownerID;
    private boolean isPrivate;
    private String publicDescription;
    private String privateDescription;
}
