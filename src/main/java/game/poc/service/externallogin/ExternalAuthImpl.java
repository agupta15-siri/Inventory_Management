package game.poc.service.externallogin;

import game.poc.crypt.RSAEncryptionDecryptionService;
import game.poc.entity.ExternalAuth;
import game.poc.entity.UserLogin;
import game.poc.exception.ResourceNotFoundException;
import game.poc.repository.ExternalAuthRepository;
import game.poc.repository.UserLoginRepository;
import game.poc.service.userlogin.UserLoginService;
import game.poc.utility.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExternalAuthImpl implements ExternalAuthService {

    private final ExternalAuthRepository externalAuthRepository;
    private final JwtToken jwtToken;

    @Override
    public void saveToken(ExternalAuth requestData) {
        Optional<ExternalAuth> dataBaseData = externalAuthRepository.findByEmail(requestData.getEmail());
        if (dataBaseData.isPresent()) {
            ExternalAuth data = dataBaseData.get();
            data.setSessionId(jwtToken.generateToken(data.getLocalId(), data.getEmail(), null));
            externalAuthRepository.save(data);
        } else {
            requestData.setSessionId(jwtToken.generateToken(requestData.getLocalId(), requestData.getEmail(), null));
            externalAuthRepository.save(requestData);
        }
    }

    @Override
    public String getAuthToken(String emailId) {
        Optional<ExternalAuth> dataBaseData = externalAuthRepository.findByEmail(emailId);
        if (dataBaseData.isPresent()) {
            return dataBaseData.get().getSessionId();
        } else
            throw new ResourceNotFoundException("Email ID not found :" + emailId);
    }
}
