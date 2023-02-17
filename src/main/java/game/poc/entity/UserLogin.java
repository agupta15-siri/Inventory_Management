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
@Table(name = "userLogin")
public class UserLogin {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "userName")
	private String userName;

	@Column(name = "userPassword")
	private String password;

	@Column(name = "userAccountId")
	private String accountID;

	@Column(name = "userEmail")
	private String email;

	@Column(name = "userSessionID")
	private String userSessionID;

}
