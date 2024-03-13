package io.muzoo.ssc.activityportal.backend.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GroupViewController {

    @Autowired
    private GroupSearchService groupSearchService;

    @GetMapping("/api/group-search/{groupID}")
    public Group fetchGroupByID(@PathVariable("groupID") long groupID) {
        return groupSearchService.fetchGroupByID(groupID);
    }
}