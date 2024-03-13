package io.muzoo.ssc.activityportal.backend.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GroupSearchController {
    @Autowired
    private GroupSearchService groupSearchService;

    @GetMapping("/api/group-search/fetch-all-groups")
    public List<Group> fetchAllGroups(){ // How about returning a new class that have group inside
        try {
            return groupSearchService.fetchAllGroups();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


}
