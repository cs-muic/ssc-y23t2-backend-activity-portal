package io.muzoo.ssc.activityportal.backend.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@Configuration
@EnableScheduling
public class GroupScheduledDelete {
    @Autowired
    GroupSetupService groupSetupService;
    @Autowired
    GroupSearchService groupSearchService;
    //every 5 minutes, check to see if an entry should be deleted
    @Scheduled(fixedDelay = 300000)
    public void scheduleFixedDelayTask() {
        groupSetupService.cleanupGroup();
    }

}
