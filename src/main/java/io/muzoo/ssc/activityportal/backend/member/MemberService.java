package io.muzoo.ssc.activityportal.backend.member;

import io.muzoo.ssc.activityportal.backend.user.User;
import io.muzoo.ssc.activityportal.backend.group.Group;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.muzoo.ssc.activityportal.backend.user.UserRepository;

@Service
public class MemberService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JoinRequestRepository joinRequestRepository;

    /**
     * Method for joining group using groupID
     * 
     * @param groupID
     * @return
     */
    public boolean joinGroup(long groupID, User u, Group currentGroup) {
        try {
            if(isMember(u, currentGroup)) return false;
            if (currentGroup.getMemberCount() >= currentGroup.getMaxMember()) return false;
            if (currentGroup.getIsPrivate() && currentGroup.getOwnerID() != u.getId()) return joinPrivateRequest(groupID, u, currentGroup);
            System.out.println(groupID + " " + u.getId() + " " + currentGroup.getOwnerID());
            u.getGroups().add(currentGroup);
            // Inject activity to the user who joins the group
            if (currentGroup.getActivities() != null) {
                u.getActivities().addAll(currentGroup.getActivities());
            }
            currentGroup.setMemberCount(currentGroup.getMemberCount()+1);
            userRepository.save(u);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage()); // DEBUG
            return false;
        }
    }
    public boolean joinPrivateRequest(long groupID, User u, Group currentGroup) {
        try {
            if (joinRequestRepository.existsByUserIDAndGroupID(u.getId(), groupID)) return false;
            JoinRequest joinRequest = new JoinRequest();
            joinRequest.setUserID(u.getId());
            joinRequest.setGroupID(groupID);
            joinRequest.setStatus(0);
            joinRequestRepository.save(joinRequest);
            return true;
        }
         catch (Exception e) {
             System.out.println(e.getMessage()); // DEBUG
             return false;
         }
    }

    public boolean leaveGroup(User currentUser, Group currentGroup) {
        try {
            if(!isMember(currentUser, currentGroup)) return false;
            if(isOwner(currentUser, currentGroup)) return false;
            currentUser.getGroups().remove(currentGroup);
            currentGroup.setMemberCount(currentGroup.getMemberCount()-1);
            // Remove all activities from the user who leaves the group
            currentUser.getActivities().removeAll(currentGroup.getActivities());
            userRepository.save(currentUser);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage()); // DEBUG
            return false;
        }
    }

    public List<Group> fetchMyGroups(User u) {
        return u.getGroups();
    }

    public boolean isOwner(User u, Group g) {
        return (u.getId() == g.getOwnerID());
    }

    public boolean isMember(User u, Group g) {
        try{
            return u.getGroups().stream().anyMatch(groups -> groups.getId() == g.getId()); // will this actually works :(
        } catch (Exception e) {
            System.out.println(e.getMessage()); // DEBUG
            return false;
        }
    }

    public List<User> getMembers(Group g) {
        return g.getMembers();
    }
}
