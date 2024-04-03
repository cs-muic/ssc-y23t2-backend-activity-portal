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
     * Function to create group.
     *
     * @param group
     * @return
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
     * Function to edit group
     *
     * @param group
     * @return
     */
    public boolean editGroup(Group group, long currentUserID) {
        try {
            System.out.println(group.getId() + " " + group.getOwnerID());
            Group currentGroup = groupSearchService.fetchGroupByID(group.getId());
            if (currentGroup.getOwnerID() != currentUserID && !deleteGroupByTime(currentGroup)) {
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
            if (groupSearchService.fetchGroupByID(groupID).getOwnerID() != currentUserID) {
                return false;
            }
            Group group = groupSearchService.fetchGroupByID(groupID);
            // Delete all the activities in the group
            Set<Activity> activities = groupSearchService.fetchGroupByID(groupID).getActivities();
            for (Activity activity : activities) {
                for (User user : activity.getUsers()) {
                    user.getActivities().remove(activity);
                    group.getActivities().remove(activity);
                }
            }
            groupRepository.deleteById(groupID);
            return true;
        } catch (Exception e) {
            System.out.println(e); // DEBUG
            return false;
        }
    }

    /**
     * deletes a single group if overtime
     *
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
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * cleans up all the groups that are overtime
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
