package io.muzoo.ssc.activityportal.backend.member;

import io.muzoo.ssc.activityportal.backend.user.User;
import io.muzoo.ssc.activityportal.backend.group.Group;
import io.muzoo.ssc.activityportal.backend.group.GroupSearchService;
import io.muzoo.ssc.activityportal.backend.whoami.WhoamiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.muzoo.ssc.activityportal.backend.SimpleResponseDTO;
import io.muzoo.ssc.activityportal.backend.user.UserRepository;

@Service
public class MemberService {
    // @Autowired
    // private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupSearchService groupSearchService;

    @Autowired
    private WhoamiService whoamiService;

    // TODO: increment user count to group when added to group
    /**
     * Method for joining group using groupID
     * 
     * @param groupID
     * @return
     */
    public SimpleResponseDTO joinGroup(long groupID) {
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

            u.getGroups().add(currentGroup);
            userRepository.save(u);

            return SimpleResponseDTO.builder()
                    .success(true)
                    .message("Joined group successfully!")
                    .build();
        } catch (Exception e) {
            System.out.println(e.getMessage()); // DEBUG
            return SimpleResponseDTO.builder()
                    .success(false)
                    .message("Unable to join group!")
                    .build();
        }
    }
}
