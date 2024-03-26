package io.muzoo.ssc.activityportal.backend.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

import io.muzoo.ssc.activityportal.backend.user.User;

@Entity
@Getter
@Setter
@Table(name = "tbl_group")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="creation_time")
    private LocalDateTime creationTime;
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
    @Column(name="tag_info")
    private String tagInfo;

    // @JsonManagedReference
    @ManyToMany(mappedBy = "groups", fetch= FetchType.LAZY)
    private Set<User> users;
}
