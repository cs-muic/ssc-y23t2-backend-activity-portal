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

//    public void createGroup(String name, int maxMember, long ownerID, boolean isPrivate, String publicDescription){
//        io.muzoo.ssc.activityportal.backend.group.Group group = groupRepository.createGroup(username);
//        if (u != null) {
//            return User.withUsername(u.getUsername())
//                    .password(u.getPassword())
//                    .roles(u.getRole())
//                    .build();
//        } else {
//            throw new UsernameNotFoundException("Invalid username or password");
//        }
//    }
//
//    }
}
