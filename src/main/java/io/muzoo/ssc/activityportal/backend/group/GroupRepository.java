package io.muzoo.ssc.activityportal.backend.group;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    final String queryGetAllGroups = "SELECT * FROM tbl_group WHERE id=%:groupID% LIMIT 1";
    
    @Query(value = queryGetAllGroups,nativeQuery=true)
    Group findGroupByID(long groupID);

}
