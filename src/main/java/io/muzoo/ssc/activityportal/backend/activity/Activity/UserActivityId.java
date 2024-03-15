package io.muzoo.ssc.activityportal.backend.activity.Activity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
@Embeddable
public class UserActivityId implements Serializable {
    private long userId;
    private long activityId;
}
