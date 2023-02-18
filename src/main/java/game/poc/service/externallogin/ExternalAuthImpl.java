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

        Optional<ExternalAuth> dataBaseData = externalAuthRepository.findByEmail(requestData.getEmail());
        ExternalAuth data;
        if (dataBaseData.isPresent()) {

            data = dataBaseData.get();
            data.setSessionId(jwtToken.generateToken(data.getLocalId(), data.getEmail(), null));
        } else {
            requestData.setSessionId(jwtToken.generateToken(requestData.getLocalId(), requestData.getEmail(), null));
            data = requestData;
        }

        externalAuthRepository.save(requestData);
        return data;
    }

    @Override
    public void saveAuthCode(String state, String code){

        ExternalAuthParam externalAuthParam = new ExternalAuthParam();
        externalAuthParam.setCode(code);
        externalAuthParam.setState(state);

        externalAuthParamRepository.save(externalAuthParam);
    }

    public String getAuthCode(String state){
        return externalAuthParamRepository.findByState(state).map(ExternalAuthParam::getCode).orElse(null);
    }
}
