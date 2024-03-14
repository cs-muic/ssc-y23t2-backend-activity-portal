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
        return groupSetupService.createGroup(group);
    }

    @PostMapping("/api/group-edit")
    public SimpleResponseDTO groupEdit(@RequestBody Group group){
        return groupSetupService.editGroup(group);
    }

    @PostMapping("/api/group-delete")
    public SimpleResponseDTO groupDelete(@RequestBody Group group){
        return null; // TMP
    }

}
