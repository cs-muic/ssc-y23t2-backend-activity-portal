package io.muzoo.ssc.activityportal.backend.group;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GroupListDTO {
    private boolean success;
    private String message;
    private List<Group> group;
}
