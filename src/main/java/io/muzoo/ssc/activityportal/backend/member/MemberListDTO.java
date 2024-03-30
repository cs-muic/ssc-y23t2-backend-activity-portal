package io.muzoo.ssc.activityportal.backend.member;

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
public class MemberListDTO {
    private boolean success;
    private String message;
    private List<User> members;
}