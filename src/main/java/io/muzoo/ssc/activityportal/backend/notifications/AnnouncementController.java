package io.muzoo.ssc.activityportal.backend.notifications;

import io.muzoo.ssc.activityportal.backend.group.Group;
import io.muzoo.ssc.activityportal.backend.group.GroupSearchService;
import io.muzoo.ssc.activityportal.backend.user.User;
import io.muzoo.ssc.activityportal.backend.whoami.WhoamiService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnnouncementController {
	@Autowired
	WhoamiService whoamiService;
	@Autowired
	GroupSearchService groupSearchService;

	@MessageMapping("/messages")
	@SendTo("/api/topic/messages")
	public AnnouncementDTO announce_send(Announcement message) throws Exception {
		System.out.println(message.getContent());
		return AnnouncementDTO.builder()
				.success(true)
				.message(message.getContent())
				.groupID(message.getGroupID())
				.username(message.getUsername())
				.build();
	}

	@PostMapping("/api/send-message")
	public AnnouncementDTO announce(HttpServletRequest request) throws Exception {
		try {
			User u = whoamiService.getCurrentUser();
			if (u == null) {
				return AnnouncementDTO.builder()
						.success(false)
						.message("User is not logged in")
						.build();
			}
			Announcement message = Announcement.builder()
					.content(request.getParameter("content"))
					.username(u.getUsername())
					.groupID(Long.parseLong(request.getParameter("groupID")))
					.build();
			announce_send(message);
			return AnnouncementDTO.builder()
					.success(true)
					.message("Message sent")
					.build();
		} catch	(Exception e){
			System.out.println(e);
			return AnnouncementDTO.builder()
					.success(false)
					.message("Error occurred")
					.build();
		}
	}
}
