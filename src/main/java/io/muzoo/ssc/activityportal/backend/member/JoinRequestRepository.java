package io.muzoo.ssc.activityportal.backend.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JoinRequestRepository extends JpaRepository<JoinRequest, Long> {
    /**
     * Check if the JoinRequest with respective userID and groupID Exists in the
     * database.
     * 
     * @param userID  (long) : The userID
     * @param groupID (long) : The groupID
     * @return true if found, false otherwise
     */
    boolean existsByUserIDAndGroupID(long userID, long groupID);

    /**
     * Fetch All JoinRequest from the database with the respective groupID
     * 
     * @param groupID (long) : The groupID
     * @return List<JoinRequest> containing all JoinRequest from the database.
     */
    List<JoinRequest> findAllByGroupID(long groupID);

    /**
     * Fetch JoinRequest from the database with the respective userID and groupID
     * 
     * @param userID  (long) : The userID
     * @param groupID (long) : The groupID
     * @return JoinRequest with the respective userID and groupID
     */
    JoinRequest findByUserIDAndGroupID(long userID, long groupID);
}
