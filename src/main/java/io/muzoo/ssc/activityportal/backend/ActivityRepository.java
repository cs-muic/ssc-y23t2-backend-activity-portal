package io.muzoo.ssc.activityportal.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    Activity findFirstById(Long id);
    Activity findFirstByName(String name);

    void deleteById(Long id);
}
