package io.muzoo.ssc.activityportal.backend.activity;

import io.muzoo.ssc.activityportal.backend.activity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    /**
     * Creates an Activity
     * @param activity the activity to be created
     * @return the created activity
     */
    Activity save(Activity activity);

    /**
     * Find the activity by the logged in userID
     * @param id the logged in userID
     * @return List of activities that logged-in user has enrolled to.
     */

    void deleteById(Long id);

    /**
     * Find the activity by the activityID
     * @param id the activityID
     * @return the activity
     */
    Activity findFirstById(Long id);

}
