package io.muzoo.ssc.activityportal.backend.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.muzoo.ssc.activityportal.backend.user.User;
import io.muzoo.ssc.activityportal.backend.member.MemberService;

import java.time.LocalDateTime;

@Service
public class GroupSetupService {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private GroupSearchService groupSearchService;
    @Autowired
    private MemberService memberService;

    /**
     * Create group using the group object with the user creating being the group owner.
     * @param group (Group) : The current group which is going to be created in the database.
     * @param user (User) : The current user.
     * @return (bool) true, false based on group creation status.
     */
    public boolean createGroup(Group group, User u) {
        try {
            groupRepository.save(group);
            memberService.joinGroup(group.getId(), u, group);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage()); // DEBUG
            return false;
        }
    }

    /**
     * Edit group based on the contents being sent along with group object.
     * @param group (Group) : The current group which is going to be edited in the database.
     * @param currentUserID (long) : The current user ID.
     * @return (bool) true, false based on group editing status.
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

    /**
     * Delete group based on groupID if the current user is the owner.
     * @param groupID (long) : The current group ID which is going to be deleted from database.
     * @param currentUserID (long) : The current user ID.
     * @return (bool) true, false based on group deletion status.
     */
    public boolean deleteGroup(long groupID, long currentUserID) {
        try {
            if(groupSearchService.fetchGroupByID(groupID).getOwnerID() != currentUserID){
                return false;
            }
            groupRepository.deleteById(groupID);
            return true;
        } catch (Exception e) {
            System.out.println(e); // DEBUG
            return false;
        }
    }

    /**
     * Check if a group has been existing overtime. 
     * @param group (Group) : the group which will be checked for deletion (+deletion).
     * @return true : overtimefalse : not overtime
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
     * Delete ALL groups that went overtime.
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
