package io.muzoo.ssc.activityportal.backend.group;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    List<Group> findAll();
    Group findFirstById(long groupID);
    void deleteById(long groupID);

}
