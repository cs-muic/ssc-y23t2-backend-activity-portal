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

}
