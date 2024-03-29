package io.muzoo.ssc.activityportal.backend.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserDTO {
    private User user;
    private boolean success;
    private String message;
}
