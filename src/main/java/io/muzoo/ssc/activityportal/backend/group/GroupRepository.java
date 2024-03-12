package io.muzoo.ssc.activityportal.backend.group;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    final String queryGetAllGroups = "SELECT * FROM Group";

    // @Query(value = queryGetAllGroups, nativeQuery = true)
    // List<Group> fetchAllGroups();

    // Group findGroupByID(long groupID);
    // @Query(value = "INSERT INTO Group ()", nativeQuery = true)
    // Group createGroup(String name, int maxMember, long ownerID, boolean isPrivate, String publicDescription);
//    void createGroup(String name, int maxMember, long ownerID, boolean isPrivate, String publicDescription, String privateDescription);
}
