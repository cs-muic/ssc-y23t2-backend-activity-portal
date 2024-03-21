package io.muzoo.ssc.activityportal.backend.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO:
 *  - Use a DTO for group to recieve data.
 */

@Service
public class GroupSearchService {
    @Autowired
    private GroupRepository groupRepository;

    public List<Group> fetchAllGroups(){

        return groupRepository.findAll();
    }

    public List<Group> fetchAllGroupsByTime(){
        return groupRepository.findAllByOrderByCreationTimeAsc();
    }

    public Group fetchGroupByID(long groupID){
        try {
            return groupRepository.findFirstById(groupID);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
