package io.muzoo.ssc.activityportal.backend;

import io.muzoo.ssc.activityportal.backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findFirstByUsername(String username);

}
