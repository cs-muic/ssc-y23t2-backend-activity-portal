package io.muzoo.ssc.activityportal.backend.member;

import io.muzoo.ssc.activityportal.backend.group.Group;
import io.muzoo.ssc.activityportal.backend.group.GroupListDTO;
import io.muzoo.ssc.activityportal.backend.group.GroupRoleDTO;
import io.muzoo.ssc.activityportal.backend.group.GroupSearchService;
import io.muzoo.ssc.activityportal.backend.group.GroupSetupService;
import io.muzoo.ssc.activityportal.backend.user.User;
import io.muzoo.ssc.activityportal.backend.whoami.WhoamiService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.muzoo.ssc.activityportal.backend.SimpleResponseDTO;

@RestController
public class MemberController {
    @Autowired
    private GroupSearchService groupSearchService;

    @Autowired
    private WhoamiService whoamiService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private GroupSetupService groupSetupService;

    private SimpleResponseDTO validityChecking(Group currentGroup, User u) {
        if (currentGroup == null) {
            return SimpleResponseDTO.builder()
                    .success(false)
                    .message("Group does not exist.")
                    .build();
        } else if (u == null) {
            return SimpleResponseDTO.builder()
                    .success(false)
                    .message("User is not logged in")
                    .build();
        }
        return null;
    }

    @PostMapping("/api/group-join/{groupID}")
    public SimpleResponseDTO joinGroup(@PathVariable long groupID) {
        try {
            Group currentGroup = groupSearchService.fetchGroupByID(groupID);
            User u = whoamiService.getCurrentUser();
            SimpleResponseDTO validityCheck = validityChecking(currentGroup, u);

            if (validityCheck != null)
                return validityCheck;
            if (memberService.joinGroup(groupID, u, currentGroup)) {
                return SimpleResponseDTO.builder()
                        .success(true)
                        .message("Joined group successfully!")
                        .build();
            } else {
                return SimpleResponseDTO.builder()
                        .success(false)
                        .message("Unable to join group!")
                        .build();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage()); // DEBUG
            return SimpleResponseDTO.builder()
                    .success(false)
                    .message("Unable to join group!")
                    .build();
        }
    }

    @PostMapping("/api/group-leave/{groupID}")
    public SimpleResponseDTO leaveGroup(@PathVariable long groupID) {
        try {
            Group currentGroup = groupSearchService.fetchGroupByID(groupID);
            User u = whoamiService.getCurrentUser();
            SimpleResponseDTO validityCheck = validityChecking(currentGroup, u);

            if (validityCheck != null)
                return validityCheck;

            if (memberService.leaveGroup(u, currentGroup)) {
                return SimpleResponseDTO.builder()
                        .success(true)
                        .message("Leave group successfully!")
                        .build();
            } else {
                return SimpleResponseDTO.builder()
                        .success(false)
                        .message("Unable to leave group!")
                        .build();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage()); // DEBUG
            return SimpleResponseDTO.builder()
                    .success(false)
                    .message("Unable to leave group!")
                    .build();
        }
    }

    @GetMapping("/api/fetch-my-groups")
    public GroupListDTO fetchMyGroups() {
        try {
            groupSetupService.cleanupGroup();
            User u = whoamiService.getCurrentUser();
            List<Group> groupList = memberService.fetchMyGroups(u);
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

    @GetMapping("/api/get-group-role/{groupID}")
    public GroupRoleDTO getGroupRole(@PathVariable long groupID){
        try{
            Group currentGroup = groupSearchService.fetchGroupByID(groupID);
            User u = whoamiService.getCurrentUser();
            SimpleResponseDTO validityCheck = validityChecking(currentGroup, u);
            if ( validityCheck != null ){
                return GroupRoleDTO.builder()
                .success(false)
                .message("failed to get group role.")
                .isOwner(false)
                .isMember(false)
                .build();
            }

            boolean isMember = memberService.isMember(u, currentGroup);
            boolean isOwner = memberService.isOwner(u, currentGroup);
            return GroupRoleDTO.builder()
                .success(true)
                .message("successfully get group role.")
                .isOwner(isOwner)
                .isMember(isMember)
                .build();

        } catch (Exception e) {

        return GroupRoleDTO.builder()
            .success(false)
            .message("failed to get group role.")
            .isOwner(false)
            .isMember(false)
            .build();
        }
    }

    @GetMapping("/api/get-group-members/{groupID}")
    public MemberListDTO getGroupMembers(@PathVariable long groupID){
        try{
            Group currentGroup = groupSearchService.fetchGroupByID(groupID);
            if ( currentGroup == null ){
                return MemberListDTO.builder()
                        .success(false)
                        .message("no such group")
                        .build();
            }
            List<User> memberList = memberService.getMembers(currentGroup);
            return MemberListDTO.builder()
                    .success(true)
                    .message("successfully get members.")
                    .members(memberList)
                    .build();

        } catch (Exception e) {

            return MemberListDTO.builder()
                    .success(false)
                    .message("failed to get members")
                    .build();
        }
    }
}
