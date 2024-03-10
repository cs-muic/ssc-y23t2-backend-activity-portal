package io.muzoo.ssc.activityportal.backend;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    Activity findFirstByActivityName(String activityName);
    Activity findFirstByActivityId(Long activityId);
    void deleteByActivityId(Long activityId);
}
