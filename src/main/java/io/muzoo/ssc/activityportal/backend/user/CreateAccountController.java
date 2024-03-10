package io.muzoo.ssc.activityportal.backend.user;

import io.muzoo.ssc.activityportal.backend.SimpleResponseDTO;
import io.muzoo.ssc.activityportal.backend.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateAccountController {

    @Autowired
    private CreateAccountService createAccountService;


    @PostMapping("/api/create-account")
    public SimpleResponseDTO signup(@RequestBody User user) {
        if (createAccountService.createAccount(user)) {
            return SimpleResponseDTO.builder()
                    .success(true)
                    .message("Account created successfully")
                    .build();
        } else {
            return SimpleResponseDTO.builder()
                    .success(false)
                    .message("Failed to create account")
                    .build();
        }
    }
}
