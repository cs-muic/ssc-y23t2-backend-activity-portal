package io.muzoo.ssc.activityportal.backend.notifications;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.muzoo.ssc.activityportal.backend.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AnnouncementDTO {
    private boolean success;
    private long groupID;
    private String username;
    private String message;
}
