package io.muzoo.ssc.activityportal.backend.member;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JoinRequestUser {
    private JoinRequest joinRequest;
    private String username;
    private String displayName;

}
