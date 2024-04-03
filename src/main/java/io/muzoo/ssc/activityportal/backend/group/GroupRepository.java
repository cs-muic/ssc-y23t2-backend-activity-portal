package io.muzoo.ssc.activityportal.backend.group;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    /**
     * Fetch all Groups from the database.
     * @return List<Group> of all groups.
     */
    List<Group> findAll();

    /**
     * Fetch all Groups from the database sorted by creation time.
     * @return List<Group> containing all groups from database by creation time.
     */
    List<Group> findAllByOrderByCreationTimeAsc();

    /**
     * Fetch the first group with respective groupID (there can only be one group) from the database.
     * @param groupID (long) : The groupID
     * @return Group with the respective groupID
     */
    Group findFirstById(long groupID);

    /**
     * Delete the group with respective groupID (there can only be one group) from the database.
     * @param groupID (long) : The groupID
     * @return Group with the respective groupID
     */
    void deleteById(long groupID);

}
