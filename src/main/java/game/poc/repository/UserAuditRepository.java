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

    @Query(value="Select A.user_email, A.Session_Id, B.Login_Count, Ui.asset_name, Ui.inventory from (Select user_email, session_id from external_auth\n" +
            "UNION ALL\n" +
            "Select user_email, user_sessionid From user_login) A\n" +
            "LEFT JOIN (Select user_email, count(*) AS Login_Count from user_audit where user_email != '' and user_action in ('E_USER_LOGIN', 'USER_LOGIN') group by user_email) B\n" +
            "\ton A.user_email = B.user_email\n" +
            "LEFT JOIN user_inventory ui \n" +
            "\ton ui.user_email = A.user_email\n" +
            "Order by B.Login_Count desc", nativeQuery = true)
    List<String> findByUserAccess();
}
