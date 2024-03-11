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
    @Column(name="group_name")
    private String groupName;
    @Column(name="max_member")
    private int maxMember;
    @Column(name="member_count")
    private int memberCount;
    @Column(name="owner_ID")
    private long ownerID;
    @Column(name="is_private")
    private Boolean isPrivate;
    @Column(name="public_description")
    private String publicDescription;
    @Column(name="private_description")
    private String privateDescription;
}
