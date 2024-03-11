package io.muzoo.ssc.activityportal.backend.whoami;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class WhoamiDTO {

    private boolean isLoggedIn = false;
    private String username = null;
    private String role = "";
    private String displayName = null;
    private Long userID = null;
}
