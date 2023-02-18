package game.poc.service.externallogin;

import game.poc.entity.ExternalAuth;
import game.poc.entity.ExternalAuthParam;
import game.poc.repository.ExternalAuthDetailsRepository;
import game.poc.repository.ExternalAuthRepository;
import game.poc.utility.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExternalAuthImpl implements ExternalAuthService {

    private final JwtToken jwtToken;
    private final ExternalAuthRepository externalAuthRepository;
    private final ExternalAuthDetailsRepository externalAuthParamRepository;

    @Override
    public ExternalAuth saveToken(ExternalAuth requestData) {


        ExternalAuth data = externalAuthRepository
                .findByEmail(requestData.getEmail())
                .orElse(requestData);

        data.setSessionId(jwtToken.generateToken(data.getLocalId(), data.getEmail(), null));

        return externalAuthRepository.save(data);
    }

    @Override
    public void saveAuthCode(String state, String code){

        ExternalAuthParam externalAuthParam = new ExternalAuthParam();
        externalAuthParam.setCode(code);
        externalAuthParam.setState(state);

        externalAuthParamRepository.save(externalAuthParam);
    }

    public String getAuthCode(String state){

        int maxWaitTime = 10000;
        int waitTime = 1000;
        int i = 1;
        String authCode = externalAuthParamRepository.findByState(state).map(ExternalAuthParam::getCode).orElse(null);

        while (authCode == null && (waitTime * i) < maxWaitTime){

            try{
                Thread.sleep(waitTime);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            i++;
            authCode = externalAuthParamRepository.findByState(state).map(ExternalAuthParam::getCode).orElse(null);
        }
        return authCode;
    }
}
