package io.muzoo.ssc.activityportal.backend.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JoinRequestRepository extends JpaRepository<JoinRequest, Long> {
    boolean existsByUserIDAndGroupID(long userID, long groupID);
    List<JoinRequest> findAllByGroupIDAndStatus(long groupID, int i);
}
