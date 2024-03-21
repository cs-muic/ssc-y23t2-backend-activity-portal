package io.muzoo.ssc.activityportal.backend.group;

import io.muzoo.ssc.activityportal.backend.user.User;
import io.muzoo.ssc.activityportal.backend.whoami.WhoamiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.muzoo.ssc.activityportal.backend.SimpleResponseDTO;

import java.time.LocalDateTime;

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
            System.out.println(group.getId() + " " + group.getOwnerID());
            Group currentGroup = groupSearchService.fetchGroupByID(group.getId());
            if(currentGroup.getOwnerID() != currentUserID && !deleteGroupByTime(currentGroup)){
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

    /**
     * deletes a single group if overtime
     * @param group
     * @return true if overtime
     */
    public boolean deleteGroupByTime(Group group) {
        try {
            LocalDateTime threeDaysAgo = LocalDateTime.now().minusDays(3);
            if (group.getCreationTime().isBefore(threeDaysAgo)) {
                groupRepository.deleteById(group.getId());
                System.out.println("DELETED GROUP WITH ID: " + group.getId());
                return true;
            }
            return false;
        } catch(Exception e) {
            return false;
        }
    }

    /**
     * cleans up all the groups that are overtime
     */
    public void cleanupGroup() {
        try {
            for (Group group:
                    groupSearchService.fetchAllGroupsByTime()) {
                deleteGroupByTime(group);
            }
        } catch(Exception e) {
        }
    }
}
