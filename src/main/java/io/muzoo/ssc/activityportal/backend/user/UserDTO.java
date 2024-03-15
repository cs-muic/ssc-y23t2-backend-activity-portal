package io.muzoo.ssc.activityportal.backend.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.muzoo.ssc.activityportal.backend.activity.Activity;
import io.muzoo.ssc.activityportal.backend.group.Group;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserDTO {
    private User user;
    private boolean success;
    private String message;
}
