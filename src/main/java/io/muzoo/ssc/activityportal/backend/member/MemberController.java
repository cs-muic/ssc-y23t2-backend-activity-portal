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

    /**
     * Check the validity(existence) of the Group and the User 
     * @param currentGroup (Group) : The current group
     * @param u (User) : The current user
     * @return null when successful. 
     *          SimpleResponseDTO when failed to validate, 
     */
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

    /**
     * API for user to join group.
     * @param groupID (long) : The group ID the user wants to join
     * @return SimpleResponseDTO containing whether group joining is successful or unsuccessful.
     */
    @PostMapping("/api/group-join/{groupID}")
    public SimpleResponseDTO joinGroup(@PathVariable long groupID) {
        try {
            Group currentGroup = groupSearchService.fetchGroupByID(groupID);
            User u = whoamiService.getCurrentUser();
            SimpleResponseDTO validityCheck = validityChecking(currentGroup, u);
            if (validityCheck != null)
                return validityCheck;
                
            if(memberService.groupIsFull(currentGroup)) {
                return SimpleResponseDTO.builder()
                .success(false)
                .message("Group is full!")
                .build();
            }


            if (memberService.joinGroup(u, currentGroup)) {
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

    /**
     * API for user to leave group.
     * @param groupID (long) : The group ID the user wants to leave
     * @return SimpleResponseDTO containing whether group leaving is successful or unsuccessful.
     */
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

    /**
     * API for fetching all current user's groups.
     * @return GroupListDTO containing all the group this user is a member of.
     */
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

    /**
     * API for fetching the current user's role inside the group.
     * @param groupID (long) : The group ID.
     * @return GroupRoleDTO containing the user's role in the group.
     */
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

    /**
     * API for fetching a list of all group members.
     * @param groupID (long) : The group ID
     * @return MemberListDTO containing the List of all users in the group.
     */
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

    /**
     * API for fetching the pending requests of current group (List of users requesting to join the group.)
     * @param groupID (long) : The group ID
     * @return JoinRequestDTO containing the list of all users requesting to join the group. 
     */
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

    /**
     * API for accepting users into the group.
     * @param groupID (long) : The group ID
     * @return SimpleResponseDTO containing the status whether the user is successfully accepted or not.
     */
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
            SimpleResponseDTO validityCheck = validityChecking(currentGroup, u);
            if (validityCheck != null)
                return validityCheck;

            if(memberService.groupIsFull(currentGroup)) {
                return SimpleResponseDTO.builder()
                .success(false)
                .message("Group is full!")
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

        /**
     * API for rejecting users from the group.
     * @param groupID (long) : The group ID
     * @return SimpleResponseDTO containing the status whether the user is successfully rejected or not.
     */
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

    /**
     * API for kicking users from the group.
     * @param groupID (long) : The groupID
     * @param userID (long) : The userID of the user who will be kicked
     * @return SimpleResponseDTO containing the status whether the kicking is succcessful or not.
     */
    @PostMapping("/api/kick-member/{groupID}/{userID}")
    public SimpleResponseDTO kickMember(@PathVariable long groupID, @PathVariable long userID){
        try {
            if (memberService.kickMember(groupID, userID)) {
                return SimpleResponseDTO.builder()
                        .success(true)
                        .message("Successfully kicked member.")
                        .build();
            } else {
                return SimpleResponseDTO.builder()
                        .success(false)
                        .message("Failed to kick member.")
                        .build();
            }
        } catch (Exception e) {
            return SimpleResponseDTO.builder()
                    .success(false)
                    .message("Failed to kick member.")
                    .build();
        }
    }
}
