package io.muzoo.ssc.activityportal.backend.group;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.muzoo.ssc.activityportal.backend.activity.Activity;
import io.muzoo.ssc.activityportal.backend.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "tbl_group")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "creation_time")
    private LocalDateTime creationTime;
    @Column(name = "group_name")
    private String groupName;
    @Column(name = "max_member")
    private int maxMember;
    @Column(name = "member_count")
    private int memberCount;
    @Column(name = "owner_ID")
    private long ownerID;
    @Column(name = "is_private")
    private Boolean isPrivate;
    @Column(name = "public_description")
    private String publicDescription;
    @Column(name = "private_description")
    private String privateDescription;

    // @JsonManagedReference
    @ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY)
    private List<User> users;

    @PreRemove
    private void removeUserAssociations() {
        for (User user : this.getUsers()) {
            user.getGroups().remove(this);
        }
    }
    // The parent in the relationship is the Group, and the child is the Activity, orphanRemoval means that if the parent is removed, the child will also be removed.
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonBackReference
    private Set<Activity> activities;
}