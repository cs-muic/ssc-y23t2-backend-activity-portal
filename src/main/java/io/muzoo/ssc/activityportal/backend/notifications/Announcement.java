package io.muzoo.ssc.activityportal.backend.notifications;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

import java.beans.ConstructorProperties;

@Getter
@Setter
@Builder
@Jacksonized
public class Announcement {
	private String username;
	private String content;
	private long groupID;
}
