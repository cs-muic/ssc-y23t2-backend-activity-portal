package io.muzoo.ssc.activityportal.backend.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupSearchService {
    @Autowired
    private GroupRepository groupRepository;

    /**
     * Fetch all groups.
     * @return List<Group> of All groups.
     */
    public List<Group> fetchAllGroups(){
        return groupRepository.findAll();
    }

    /**
     * Fetch all groups sorted by creation time.
     * @return List<Group> of All groups sorted by creation time.
     */
    public List<Group> fetchAllGroupsByTime(){
        return groupRepository.findAllByOrderByCreationTimeAsc();
    }

    /**
     * Fetch Group with the respective ID.
     * @param groupID (long) : The ID of the group.
     * @return Group with the respective group ID.
     */
    public Group fetchGroupByID(long groupID){
        try {
            return groupRepository.findFirstById(groupID);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
