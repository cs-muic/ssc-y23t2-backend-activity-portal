package io.muzoo.ssc.activityportal.backend.group;

import io.muzoo.ssc.activityportal.backend.user.User;
import io.muzoo.ssc.activityportal.backend.whoami.WhoamiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.muzoo.ssc.activityportal.backend.SimpleResponseDTO;

/**
 * TODO: 
 *  - Use a DTO for group to recieve data.
 */

@Service
public class GroupSetupService {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private GroupSearchService groupSearchService;
    @Autowired
    private WhoamiService whoamiService;

    /**
     * Function to create group.
     * @param group
     * @return 
     */
    public boolean createGroup(Group group) {
        try {
            groupRepository.save(group);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage()); // DEBUG
            return false;
        }
    }

    /**
     * Function to edit group
     * @param group
     * @return
     */
    public boolean editGroup(Group group, long currentUserID) {
        try {

            Group currentGroup = groupSearchService.fetchGroupByID(group.getId());
            if(currentGroup.getOwnerID() != currentUserID){
                return false;
            }
            group.setMaxMember(currentGroup.getMaxMember());
            groupRepository.save(group);
            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage()); // DEBUG
            return false;
        }
    }

    public boolean deleteGroup(long groupID, long currentUserID) {
        try {
            if(groupSearchService.fetchGroupByID(groupID).getOwnerID() != currentUserID){
                return false;
            }
            groupRepository.deleteById(groupID);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
