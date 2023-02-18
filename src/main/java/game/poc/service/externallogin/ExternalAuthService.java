package game.poc.service.externallogin;

import game.poc.entity.ExternalAuth;
import game.poc.entity.UserLogin;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ExternalAuthService {
    ExternalAuth saveToken(ExternalAuth externalAuth);
    void saveAuthCode(String state, String code);
    String getAuthCode(String state);
}
