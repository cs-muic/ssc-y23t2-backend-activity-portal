package io.muzoo.ssc.activityportal.backend.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupSearchService {
    @Autowired
    private GroupRepository groupRepository;

    public List<Group> fetchAllGroups(){
        return groupRepository.findAll();
    }

    public Group fetchGroupByID(long groupID){
        try {
            return groupRepository.findGroupByID(groupID);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
