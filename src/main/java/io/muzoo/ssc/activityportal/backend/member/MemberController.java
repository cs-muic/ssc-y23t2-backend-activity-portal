package io.muzoo.ssc.activityportal.backend.member;

import io.muzoo.ssc.activityportal.backend.group.Group;
import io.muzoo.ssc.activityportal.backend.group.GroupSearchService;
import io.muzoo.ssc.activityportal.backend.user.User;
import io.muzoo.ssc.activityportal.backend.whoami.WhoamiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.muzoo.ssc.activityportal.backend.SimpleResponseDTO;

@RestController
public class MemberController {
    @Autowired
    private GroupSearchService groupSearchService;

    @Autowired
    private WhoamiService whoamiService;

    @Autowired
    private MemberService memberService;

    @PostMapping("/api/group-join/{groupID}")
    public SimpleResponseDTO joinGroup(@PathVariable long groupID){
        //TODO: uncomment this when group-join works without ruining everything
        try {
            Group currentGroup = groupSearchService.fetchGroupByID(groupID);
            if (currentGroup == null) {
                return SimpleResponseDTO.builder()
                        .success(false)
                        .message("Group does not exist.")
                        .build();
            }

            User u = whoamiService.getCurrentUser();
            if(u == null) {
                return SimpleResponseDTO.builder()
                        .success(false)
                        .message("User is not logged in")
                        .build();
            }
            if(memberService.joinGroup(groupID, u, currentGroup)){
                return SimpleResponseDTO.builder()
                        .success(true)
                        .message("Joined group successfully!")
                        .build();
            } else {
                return SimpleResponseDTO.builder()
                        .success(false)
                        .message("Unable to join group!")
                        .build();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage()); // DEBUG
            return SimpleResponseDTO.builder()
                    .success(false)
                    .message("Unable to join group!")
                    .build();
        }
    }
}
