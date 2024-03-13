package io.muzoo.ssc.activityportal.backend.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.muzoo.ssc.activityportal.backend.UserRepository;
import io.muzoo.ssc.activityportal.backend.SimpleResponseDTO;

@Service
public class GroupSetupService {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private GroupSearchService groupSearchService;
    // TODO: TEMP <Maybe we can use the authentication service 
    // from elsewhere instead of doing it on repository> 
    // <is it actually necessary??>
    @Autowired
    private UserRepository userRepository;

    /**
     * Function to create group.
     * @param group
     * @return 
     */
    public SimpleResponseDTO createGroup(Group group) {
        try {
            groupRepository.save(group);
            return SimpleResponseDTO.builder()
                    .success(true)
                    .message("group created successfully!")
                    .build();

        } catch (Exception e) {
            System.out.println(e.getMessage()); // DEBUG
            return SimpleResponseDTO.builder()
                    .success(false)
                    .message("unable to create group!")
                    .build();
        }
    }

    /**
     * Function to edit group
     * @param group
     * @return
     */
    public SimpleResponseDTO editGroup(Group group) {
        try {
            // TODO: Temp <Maybe this will be safer than using store?>  
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            long currentUserID = userRepository.findFirstByUsername(((UserDetails) principal).getUsername()).getId();
            if(groupSearchService.fetchGroupByID(group.getId()).getOwnerID() != currentUserID){
                return SimpleResponseDTO.builder()
                .success(false)
                .message("Failed to authenticate!")
                .build();
            }
            groupRepository.save(group);
            return SimpleResponseDTO.builder()
                    .success(true)
                    .message("Group has been edited successfully!")
                    .build();

        } catch (Exception e) {
            System.out.println(e.getMessage()); // DEBUG
            return SimpleResponseDTO.builder()
                    .success(false)
                    .message("unable to edit group!")
                    .build();
        }
    }
}
