package io.muzoo.ssc.activityportal.backend.user;


import io.muzoo.ssc.activityportal.backend.SimpleResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EditProfileController {

    @Autowired
    private EditProfileService editProfileService;

    @PostMapping("/api/edit-profile")
    public SimpleResponseDTO editProfile(@RequestBody User user) {
        if (editProfileService.editProfile(user)) {
            return SimpleResponseDTO.builder()
                    .success(true)
                    .message("Profile edited successfully")
                    .build();
        } else {
            return SimpleResponseDTO.builder()
                    .success(false)
                    .message("Failed to edit profile")
                    .build();
        }
    }
}
