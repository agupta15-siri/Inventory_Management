package game.poc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "UserAudit")
public class UserAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "audit_id")
    private long auditId;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_action")
    private String userAction;

    @Column(name = "event_time")
    private LocalDateTime eventTime;

    @Column(name = "event_id")
    private String eventId;

}
