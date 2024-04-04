package io.muzoo.ssc.activityportal.backend.group;

import io.muzoo.ssc.activityportal.backend.activity.Activity;
import io.muzoo.ssc.activityportal.backend.member.MemberService;
import io.muzoo.ssc.activityportal.backend.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

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
     * @param u (User) : The current user.
     * @return (bool) true, false based on group creation status.
     */
    public boolean createGroup(Group group, User u) {
        try {
            groupRepository.save(group);
            memberService.joinGroup(u, group);
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
            if (currentGroup.getOwnerID() != currentUserID && !deleteGroupByTime(currentGroup)) {
                return false;
            }
            currentGroup.setGroupName(group.getGroupName());
            currentGroup.setPublicDescription(group.getPublicDescription());
            currentGroup.setPrivateDescription(group.getPrivateDescription());
            currentGroup.setIsPrivate(group.getIsPrivate());
            groupRepository.save(currentGroup);
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
            if (groupSearchService.fetchGroupByID(groupID).getOwnerID() != currentUserID) {
                return false;
            }
            Group group = groupSearchService.fetchGroupByID(groupID);
            // Delete all the activities in the group
            Set<Activity> activities = group.getActivities();
            for (Activity activity : activities) {
                for (User user : activity.getUsers()) {
                    user.getActivities().remove(activity);
                }
                activity.setGroup(null);
            }
            group.removeUserAssociations();
            group.getActivities().clear();
            groupRepository.save(group);
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
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Delete ALL groups that went overtime.
     */
    public void cleanupGroup() {
        try {
            for (Group group :
                    groupSearchService.fetchAllGroupsByTime()) {
                deleteGroupByTime(group);
            }
        } catch (Exception e) {
        }
    }
}
