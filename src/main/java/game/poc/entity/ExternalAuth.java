package game.poc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ExternalAuth")
public class ExternalAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "localId")
    private String localId;

    @Column(name = "idToken")
    private String idToken;

    @Column(name = "sessionId")
    private String sessionId;

    @Column(name = "userEmail")
    private String email;
}
