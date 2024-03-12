package io.muzoo.ssc.activityportal.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    Activity save(Activity activity);

    void deleteById(Long id);


}
