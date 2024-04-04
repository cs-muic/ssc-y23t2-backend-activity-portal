package io.muzoo.ssc.activityportal.backend.member;

import io.muzoo.ssc.activityportal.backend.group.GroupRepository;
import io.muzoo.ssc.activityportal.backend.user.User;
import io.muzoo.ssc.activityportal.backend.group.Group;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.muzoo.ssc.activityportal.backend.user.UserRepository;

@Service
public class MemberService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JoinRequestRepository joinRequestRepository;
    @Autowired
    private GroupRepository groupRepository;

    /**
     * Let the User join the Group
     * 
     * @param u     (User) : The user trying to join the group.
     * @param group (Group) : The group the user is joining.
     * @return true if succcessful, false if unsuccessful.
     */
    public boolean joinGroup(User u, Group currentGroup) {
        try {
            System.out.println("GroupID:" + currentGroup.getId() + ", UserID:" + u.getId() + ", OwnerID:"
                    + currentGroup.getOwnerID()); // DEBUG

            if (isMember(u, currentGroup))
                return false;
            if (currentGroup.getMemberCount() >= currentGroup.getMaxMember())
                return false;
            if (currentGroup.getIsPrivate() && currentGroup.getOwnerID() != u.getId())
                return joinPrivateRequest(currentGroup.getId(), u);

            return addUserToGroup(u, currentGroup);
        } catch (Exception e) {
            System.out.println(e.getMessage()); // DEBUG
            return false;
        }
    }

    /**
     * Add the user to the current group
     * 
     * @param u            (User) : The user in which will be added to the group.
     * @param currentGroup (Group) : The group the user will be added into.
     * @return true if successful, false if unsuccessful
     */
    public boolean addUserToGroup(User u, Group currentGroup) {
        try {
            u.getGroups().add(currentGroup);
            // Inject activity to the user who joins the group
            if (currentGroup.getActivities() != null) {
                u.getActivities().addAll(currentGroup.getActivities());
            }
            currentGroup.setMemberCount(currentGroup.getMemberCount() + 1);
            userRepository.save(u);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Let the User make join request to private Group
     * 
     * @param u       (User) : The user requesting to join the group.
     * @param groupID (long) : The groupID of the group the user is joining.
     * @return true if succcessful, false if unsuccessful.
     */
    public boolean joinPrivateRequest(long groupID, User u) {
        try {
            if (joinRequestRepository.existsByUserIDAndGroupID(u.getId(), groupID))
                return false;
            JoinRequest joinRequest = new JoinRequest();
            joinRequest.setUserID(u.getId());
            joinRequest.setGroupID(groupID);
            joinRequestRepository.save(joinRequest);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage()); // DEBUG
            return false;
        }
    }

    /**
     * Let the User leave the Group
     * 
     * @param u     (User) : The user trying to leave the group.
     * @param group (Group) : The group the user is leaving.
     * @return true if the user successfully left the group.
     */
    public boolean leaveGroup(User u, Group group) {
        try {
            if (!isMember(u, group))
                return false;
            if (isOwner(u, group))
                return false;
            u.getGroups().remove(group);

            // Reduce the number of members in the group
            group.setMemberCount(group.getMemberCount() - 1);

            // Remove all activities from the user who leaves the group
            u.getActivities().removeAll(group.getActivities());
            userRepository.save(u);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage()); // DEBUG
            return false;
        }
    }

    /**
     * Let the Owner kickk a user from the Group
     * 
     * @param groupID (long) : The ID of the group.
     * @param userID  (long) : The ID of the user getting kicked.
     * @return true if the user is successfully kicked.
     */
    public boolean kickMember(long groupID, long userID) {
        try {
            System.out.println(groupID + " " + userID);
            User user = userRepository.findById(userID).orElse(null);
            Group group = groupRepository.findById(groupID).orElse(null);
            if (user == null || group == null)
                return false;
            if (!isMember(user, group))
                return false;
            user.getGroups().remove(group);
            group.setMemberCount(group.getMemberCount() - 1);
            user.getActivities().removeAll(group.getActivities());
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage()); // DEBUG
            return false;
        }
    }

    /**
     * Fetch all of the Groups the User is a member of.
     * 
     * @param u (User) : The current user.
     * @return List<Group> of the groups where the user is a member of.
     */
    public List<Group> fetchMyGroups(User u) {
        return u.getGroups();
    }

    /**
     * Check if the current user is the owner of the group.
     * 
     * @param u (User) : The current user.
     * @param g (Group) : The current group.
     * @return true if the user is the owner of the group.
     */
    public boolean isOwner(User u, Group g) {
        return (u.getId() == g.getOwnerID());
    }

    /**
     * Check if the current user is a member of the group.
     * 
     * @param u (User) : The current user.
     * @param g (Group) : The current group.
     * @return true if the user is a member of the group.
     */
    public boolean isMember(User u, Group g) {
        try {
            return u.getGroups().stream().anyMatch(groups -> groups.getId() == g.getId()); // will this actually works
                                                                                           // :(
        } catch (Exception e) {
            System.out.println(e.getMessage()); // DEBUG
            return false;
        }
    }

    /**
     * Get a list of members of the group.
     * 
     * @param g (Group): The current group we want to get a list of members.
     * @return List<User> of group members.
     */
    public List<User> getMembers(Group g) {
        return g.getMembers();
    }

    /**
     * Check whether a group is full
     * 
     * @param g (Group): The current group.
     * @return true if the group is full.
     */
    public boolean groupIsFull(Group g) {
        return (g.getMemberCount() == g.getMaxMember());
    }

    /**
     * Fetch join Requests based on userID,groupID pair
     * 
     * @param userID  (long) : The id of the user.
     * @param groupID (long) : The id of the group.
     * @return JoinRequest which matches the ID pair.
     */
    JoinRequest fetchJoinRequestByPairID(long userID, long groupID) {
        return joinRequestRepository.findByUserIDAndGroupID(userID, groupID);
    }

    /**
     * Delete join Request based on the object JoinRequest.
     * 
     * @param joinRequest (JoinRequest) : The request which will be deleted
     * @return true if successful, false if unsuccessful.
     */
    boolean deleteJoinRequest(JoinRequest joinRequest) {
        try {
            joinRequestRepository.delete(joinRequest);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage()); // DEBUG
            return false;
        }
    }

    /**
     * Fetch all of the JoinRequest of the Group
     * 
     * @param groupID (long) : The ID of the group we wish to fetch its JoinRequests
     * @return List containing all JoinRequest of the Group.
     */
    List<JoinRequest> fetchAllJoinRequestByGroupID(long groupID) {
        return joinRequestRepository.findAllByGroupID(groupID);
    }

    /**
     * Get All JoinRequestUser based on GroupID
     * 
     * @param groupID (long) : The ID of the group we wish to retrieve the
     *                JoinRequestUsers
     * @return List of JoinRequestUser
     */
    List<JoinRequestUser> getAllJoinRequestUserByGroupID(long groupID) {
        List<JoinRequest> requestList = fetchAllJoinRequestByGroupID(groupID);
        List<JoinRequestUser> joinRequestUsers = requestList.stream().map(request -> {
            User user = userRepository.findById(request.getUserID()).orElse(null);
            assert user != null;
            JoinRequestUser joinRequestUser = new JoinRequestUser();
            joinRequestUser.setJoinRequest(request);
            joinRequestUser.setUsername(user.getUsername());
            joinRequestUser.setDisplayName(user.getDisplayName());
            return joinRequestUser;
        }).collect(Collectors.toList());
        return joinRequestUsers;
    }
}
