package io.muzoo.ssc.activityportal.backend.member;

import io.muzoo.ssc.activityportal.backend.group.Group;
import io.muzoo.ssc.activityportal.backend.group.GroupListDTO;
import io.muzoo.ssc.activityportal.backend.group.GroupRoleDTO;
import io.muzoo.ssc.activityportal.backend.group.GroupSearchService;
import io.muzoo.ssc.activityportal.backend.group.GroupSetupService;
import io.muzoo.ssc.activityportal.backend.user.User;
import io.muzoo.ssc.activityportal.backend.user.UserRepository;
import io.muzoo.ssc.activityportal.backend.whoami.WhoamiService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private JoinRequestRepository joinRequestRepository;

    @Autowired
    private UserRepository userRepository;

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

    @GetMapping("/api/get-pending-requests/{groupID}")
    public JoinRequestDTO getPendingRequests(@PathVariable long groupID){
        try{
            List<JoinRequest> requestList = joinRequestRepository.findAllByGroupID(groupID);
            List<JoinRequestUser> joinRequestUsers = requestList.stream().map(request -> {
                User user = userRepository.findById(request.getUserID()).orElse(null);
                assert user != null;
                JoinRequestUser joinRequestUser = new JoinRequestUser();
                joinRequestUser.setJoinRequest(request);
                joinRequestUser.setUsername(user.getUsername());
                joinRequestUser.setDisplayName(user.getDisplayName());
                return joinRequestUser;
            }).collect(Collectors.toList());
            return JoinRequestDTO.builder()
                    .success(true)
                    .message("successfully get pending requests.")
                    .joinRequests(joinRequestUsers)
                    .build();
        } catch (Exception e) {
            return JoinRequestDTO.builder()
                    .success(false)
                    .message("failed to get pending requests.")
                    .build();
        }
    }

    @PostMapping("/api/accept-join-request/{groupID}/{userID}")
    public SimpleResponseDTO acceptJoinRequest(@PathVariable long groupID, @PathVariable long userID){
        try {
            JoinRequest joinRequest = joinRequestRepository.findByUserIDAndGroupID(userID, groupID);
            if (joinRequest == null) {
                return SimpleResponseDTO.builder()
                        .success(false)
                        .message("Request does not exist.")
                        .build();
            }

            User u = userRepository.findById(userID).orElse(null);
            Group currentGroup = groupSearchService.fetchGroupByID(groupID);
            if (u == null || currentGroup == null) {
                return SimpleResponseDTO.builder()
                        .success(false)
                        .message("User or group does not exist.")
                        .build();
            }

            u.getGroups().add(currentGroup);
            currentGroup.setMemberCount(currentGroup.getMemberCount()+1);
            userRepository.save(u);

            joinRequestRepository.delete(joinRequest);

            return SimpleResponseDTO.builder()
                    .success(true)
                    .message("Successfully accepted request.")
                    .build();

        } catch (Exception e) {
            return SimpleResponseDTO.builder()
                    .success(false)
                    .message("Failed to accept request.")
                    .build();
        }
    }

    @PostMapping("/api/reject-join-request/{groupID}/{userID}")
    public SimpleResponseDTO rejectJoinRequest(@PathVariable long groupID, @PathVariable long userID){
        try {
            JoinRequest joinRequest = joinRequestRepository.findByUserIDAndGroupID(userID, groupID);
            if (joinRequest == null) {
                return SimpleResponseDTO.builder()
                        .success(false)
                        .message("Request does not exist.")
                        .build();
            }

            joinRequestRepository.delete(joinRequest);

            return SimpleResponseDTO.builder()
                    .success(true)
                    .message("Successfully rejected request.")
                    .build();

        } catch (Exception e) {
            return SimpleResponseDTO.builder()
                    .success(false)
                    .message("Failed to reject request.")
                    .build();
        }
    }
}
