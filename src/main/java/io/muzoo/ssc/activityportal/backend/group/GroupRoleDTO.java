package io.muzoo.ssc.activityportal.backend.group;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GroupRoleDTO {
    private boolean success;
    private boolean isOwner;
    private boolean isMember;
    private String message;
}

