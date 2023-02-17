package game.poc.service.userlogin;

import game.poc.entity.UserLogin;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserLoginService {

    List<UserLogin> getUsers();
    UserLogin getSpecificUser(String emailId);
    UserLogin userRegistration(UserLogin userLogin);
    UserLogin userAuthentication(UserLogin userLogin);

}
