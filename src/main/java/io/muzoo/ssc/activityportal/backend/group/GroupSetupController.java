package io.muzoo.ssc.activityportal.backend.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.muzoo.ssc.activityportal.backend.SimpleResponseDTO;

@RestController
public class GroupSetupController {
    @Autowired
    private GroupSetupService groupSetupService;

    @PostMapping("/api/group-create")
    public SimpleResponseDTO groupCreate(@RequestBody Group group){
        // DEBUG
        System.out.printf("Group Name: %s,\nGroup Owner ID: %d,\nGroup Description: %s,\nGroup Max member: %d,\nisPrivate: %b\n",group.getGroupName(),group.getOwnerID(),group.getPublicDescription(),group.getMaxMember(),group.getIsPrivate());

        return groupSetupService.createGroup(group);
    }

}
