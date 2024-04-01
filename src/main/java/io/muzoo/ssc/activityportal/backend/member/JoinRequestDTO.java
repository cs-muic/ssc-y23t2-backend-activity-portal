package io.muzoo.ssc.activityportal.backend.member;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JoinRequestDTO {
    private boolean success;
    private String message;
    private List<JoinRequestUser> joinRequests;
}
