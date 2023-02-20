package game.poc.repository;

import game.poc.entity.UserAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface UserAuditRepository extends JpaRepository<UserAudit, Long>{

    Optional<List<UserAudit>> findByUserAction(String userAction);

    @Query(value="Select User_Email AS EMAIL, count(*) AS LOGIN_COUNT from user_audit where user_email != '' and user_action in ('E_USER_LOGIN', 'USER_LOGIN') group by User_Email;", nativeQuery = true)
    List<String> findByUserActions();
}
