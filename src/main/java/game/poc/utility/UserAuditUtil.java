package game.poc.utility;

import game.poc.entity.UserAudit;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class UserAuditUtil {

    public static UserAudit createUserAudit(String action, String userEmail, String eventId){

        UserAudit userAudit = new UserAudit();

        userAudit.setEventTime(LocalDateTime.now());
        userAudit.setUserAction(action);
        userAudit.setUserEmail(userEmail);
        userAudit.setEventId(eventId);

        return userAudit;
    }
}
