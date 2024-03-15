package io.muzoo.ssc.activityportal.backend.group;

import io.muzoo.ssc.activityportal.backend.SimpleResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GroupSearchController {
    @Autowired
    private GroupSearchService groupSearchService;

    @GetMapping("/api/group-search/fetch-all-groups")
    public GroupListDTO fetchAllGroups(){ // How about returning a new class that have group inside
        try {
            List<Group> groupList = groupSearchService.fetchAllGroups();
            return GroupListDTO.builder()
                    .group(groupList)
                    .message("Found groups")
                    .success(true)
                    .build();
        } catch (Exception e) {
            return GroupListDTO.builder()
                    .message("Groups not found")
                    .success(false)
                    .build();
        }
    }

    @GetMapping("/api/group-search/{groupID}")
    public GroupDTO fetchGroupByID(@PathVariable("groupID") long groupID) {
        Group group = groupSearchService.fetchGroupByID(groupID);
        if(group != null){
            return GroupDTO.builder()
                    .success(true)
                    .group(group)
                    .message("Found group")
                    .build();
        } else {
            return GroupDTO.builder()
                    .success(false)
                    .message("Group not found")
                    .build();
        }
    }
}
    