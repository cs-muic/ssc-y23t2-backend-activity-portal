package io.muzoo.ssc.activityportal.backend.group;

import io.muzoo.ssc.activityportal.backend.user.User;
import io.muzoo.ssc.activityportal.backend.whoami.WhoamiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.muzoo.ssc.activityportal.backend.SimpleResponseDTO;

@RestController
public class GroupSetupController {
    @Autowired
    private GroupSetupService groupSetupService;
    @Autowired
    private WhoamiService whoamiService;

    /**
     * API for creating the group.
     * @param group (Group): The group that will be created
     * @return SimpleResponseDTO containing the status of group creation (success, failure);
     */
    @PostMapping("/api/group-create")
    public SimpleResponseDTO groupCreate(@RequestBody Group group) {
        User u = whoamiService.getCurrentUser();
        if (u == null || u.getId() != group.getOwnerID()) {
            return SimpleResponseDTO.builder()
                    .success(false)
                    .message("unable to create group!")
                    .build();
        }

        boolean result = groupSetupService.createGroup(group, u);
        if (result) {
            return SimpleResponseDTO.builder()
                    .success(true)
                    .message("group created successfully!")
                    .build();
        } else {
            return SimpleResponseDTO.builder()
                    .success(false)
                    .message("unable to create group!")
                    .build();
        }
    }

    /**
     * API for editing the contents of the group.
     * @param group (Group): The contents of the group that will be edited.
     * @return SimpleResponseDTO containing the status of group editing (success, failure);
     */
    @PostMapping("/api/group-edit")
    public SimpleResponseDTO groupEdit(@RequestBody Group group) {
        try {
            User u = whoamiService.getCurrentUser();
            if (u == null) {
                return SimpleResponseDTO.builder()
                        .success(false)
                        .message("User is not logged in")
                        .build();
            }
            long currentUserID = u.getId();
            boolean setupResultPass = groupSetupService.editGroup(group, currentUserID);
            if (setupResultPass) {
                return SimpleResponseDTO.builder()
                        .success(true)
                        .message("Group has been edited successfully!")
                        .build();
            } else {
                return SimpleResponseDTO.builder()
                        .success(true)
                        .message("Unable to edit group!")
                        .build();
            }

        } catch (Exception e) {
            return SimpleResponseDTO.builder()
                    .success(false)
                    .message("Error occurred: " + e.getMessage())
                    .build();
        }
    }

    /**
     * API for deleting the group.
     * @param group (Group): The group that will be deleted.
     * @return SimpleResponseDTO containing the status of group deleting (success, failure);
     */
    @PostMapping("/api/group-delete")
    public SimpleResponseDTO groupDelete(@RequestBody Group group) {
        try {
            User u = whoamiService.getCurrentUser();
            if (u == null) {
                return SimpleResponseDTO.builder()
                        .success(false)
                        .message("User is not logged in")
                        .build();
            }
            long currentUserID = u.getId();
            boolean deleteResultPass = groupSetupService.deleteGroup(group.getId(), currentUserID);
            if (deleteResultPass) {
                return SimpleResponseDTO.builder()
                        .success(true)
                        .message("Group has been deleted!")
                        .build();
            } else {
                return SimpleResponseDTO.builder()
                        .success(false)
                        .message("unable to delete group!")
                        .build();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage()); // DEBUG
            return SimpleResponseDTO.builder()
                    .success(false)
                    .message("Error: " + e)
                    .build();
        }
    }

}
