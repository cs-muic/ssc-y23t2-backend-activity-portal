package io.muzoo.ssc.activityportal.backend.user;


import io.muzoo.ssc.activityportal.backend.SimpleResponseDTO;
import io.muzoo.ssc.activityportal.backend.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChangePasswordController {

    @Autowired
    private ChangePasswordService changePasswordService;

    @PostMapping("/api/change-password")
    public SimpleResponseDTO editProfile(@RequestBody User user) {
        if (changePasswordService.changePassword(user)) {
            return SimpleResponseDTO.builder()
                    .success(true)
                    .message("Password changed successfully")
                    .build();
        } else {
            return SimpleResponseDTO.builder()
                    .success(false)
                    .message("Failed to change password")
                    .build();
        }
    }
}
