package io.muzoo.ssc.activityportal.backend.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.muzoo.ssc.activityportal.backend.SimpleResponseDTO;

@Service
public class GroupSetupService{
    @Autowired
    private GroupRepository groupRepository;

    public SimpleResponseDTO createGroup(Group group){
        try{
            groupRepository.save(group);
            return SimpleResponseDTO.builder()
                    .success(true)
                    .message("group created successfully!")
                    .build();

        } catch (Exception e){
            System.out.println(e.getMessage()); // DEBUG
            return SimpleResponseDTO.builder()
            .success(false)
            .message("unable to create group!")
            .build();
        }
    }
}
