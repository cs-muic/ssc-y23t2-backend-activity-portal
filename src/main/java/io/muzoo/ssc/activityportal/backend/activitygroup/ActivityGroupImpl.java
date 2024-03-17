package io.muzoo.ssc.activityportal.backend.activitygroup;

import io.muzoo.ssc.activityportal.backend.activity.Activity;
import io.muzoo.ssc.activityportal.backend.activity.ActivityDTO;
import io.muzoo.ssc.activityportal.backend.activity.ActivityRepository;
import io.muzoo.ssc.activityportal.backend.group.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ActivityGroupImpl implements ActivityGroupService {
    @Autowired
    ActivityRepository activityRepository;
    @Autowired
    GroupRepository groupRepository;

    @Override
    public Set<Activity> getGroupActivity(long groupId) {
        System.out.println(activityRepository.findAllByGroupId(groupId));
        return activityRepository.findAllByGroupId(groupId);
    }

}
