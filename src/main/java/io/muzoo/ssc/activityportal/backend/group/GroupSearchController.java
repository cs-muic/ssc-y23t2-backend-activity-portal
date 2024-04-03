package io.muzoo.ssc.activityportal.backend.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GroupSearchController {
    @Autowired
    private GroupSearchService groupSearchService;
    @Autowired
    private GroupSetupService groupSetupService;

    /**
     * API for fetching all groups in the database.
     * @return GroupListDTO contatining all groups.
     */
    @GetMapping("/api/group-search/fetch-all-groups")
    public GroupListDTO fetchAllGroups(){ 
        try {
            groupSetupService.cleanupGroup();
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

    /**
     * API for fetching group with the respective ID.
     * @param groupID (long) : The ID of the group
     * @return GroupDTO containing group with respective groupID
     */
    @GetMapping("/api/group-search/{groupID}")
    public GroupDTO fetchGroupByID(@PathVariable("groupID") long groupID) {
        Group group = groupSearchService.fetchGroupByID(groupID);
        if(group != null && !groupSetupService.deleteGroupByTime(group)){
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

    /**
     * API for fetching all groups in the database sorted based on creation time.
     * @return GroupListDTO contatining all groups sorted based on creation time.
     */
    @GetMapping("/api/group-all-time")
    public GroupListDTO fetchAllGroupsTime(){ 
        try {
            groupSetupService.cleanupGroup();
            List<Group> groupList = groupSearchService.fetchAllGroupsByTime();
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
}
    