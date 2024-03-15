package io.muzoo.ssc.activityportal.backend.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import io.muzoo.ssc.activityportal.backend.SimpleResponseDTO;
import io.muzoo.ssc.activityportal.backend.UserRepository;

@Service
public class MemberService {
    // @Autowired
    // private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupSearchService groupSearchService;


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
            // TODO: This is the reused code snippet, find a way to fix it.
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = (User) principal;
            io.muzoo.ssc.activityportal.backend.User u = userRepository.findFirstByUsername(user.getUsername());

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
