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

    // TODO: increment user count to group when user join/leave groups
    /**
     * Method for joining group using groupID
     * 
     * @param groupID
     * @return
     */
    public boolean joinGroup(long groupID, User u, Group currentGroup) {
        try {
            System.out.println(groupID + " " + u.getId() + " " + currentGroup.getOwnerID());
            u.getGroups().add(currentGroup);
            userRepository.save(u);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage()); // DEBUG
            return false;
        }
    }

    public boolean leaveGroup(User currentUser, Group currentGroup) {
        try {
            currentUser.getGroups().remove(currentGroup);
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
}
