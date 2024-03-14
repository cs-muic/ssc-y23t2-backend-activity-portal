package io.muzoo.ssc.activityportal.backend.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.muzoo.ssc.activityportal.backend.SimpleResponseDTO;

@RestController
public class MemberController {
    
    @Autowired
    private MemberService memberService;

    @PostMapping("/api/group-join/{groupID}")
    public SimpleResponseDTO joinGroup(@PathVariable long groupID){
        //TODO: uncomment this when group-join works without ruining everything 
        return null; // memberService.joinGroup(groupID); 
    }
}
