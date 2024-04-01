package io.muzoo.ssc.activityportal.backend.activity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    /**
     * Creates an Activity
     *
     * @param activity the activity to be created
     * @return the created activity
     */
    Activity save(Activity activity);

    /**
     * Find the activity by the logged in userID
     *
     * @param id the logged in userID
     * @return List of activities that logged-in user has enrolled to.
     */

    Activity findFirstById(Long id);

    /**
     * Find the activity by the groupID
     *
     * @param groupId the groupID
     * @return the activity
     */
    Set<Activity> findAllByGroupId(Long groupId);
}
