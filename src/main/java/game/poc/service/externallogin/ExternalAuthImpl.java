package game.poc.service.externallogin;

import game.poc.entity.ExternalAuth;
import game.poc.entity.ExternalAuthParam;
import game.poc.entity.UserAudit;
import game.poc.repository.ExternalAuthDetailsRepository;
import game.poc.repository.ExternalAuthRepository;
import game.poc.repository.UserAuditRepository;
import game.poc.utility.Constants;
import game.poc.utility.JwtToken;
import game.poc.utility.UserAuditUtil;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExternalAuthImpl implements ExternalAuthService {

    private final JwtToken jwtToken;
    private final ExternalAuthRepository externalAuthRepository;
    private final ExternalAuthDetailsRepository externalAuthParamRepository;
    private final UserAuditRepository userAuditRepository;

    @Override
    public ExternalAuth saveToken(ExternalAuth requestData) {

        ExternalAuth data = externalAuthRepository
                .findByEmail(requestData.getEmail())
                .orElse(requestData);

        data.setSessionId(jwtToken.generateToken(data.getLocalId(), data.getEmail(), null));

        userAuditRepository.save(UserAuditUtil.createUserAudit(Constants.AUDIT_EUSER_LOGIN, JwtToken.getTokenParam(data.getSessionId(),"email"), JwtToken.getTokenParam(data.getSessionId(),"jti")));
        return externalAuthRepository.save(data);
    }

    @Override
    public void saveAuthCode(String state, String code){

        ExternalAuthParam externalAuthParam = new ExternalAuthParam();
        externalAuthParam.setCode(code);
        externalAuthParam.setState(state);

        externalAuthParamRepository.save(externalAuthParam);
        userAuditRepository.save(UserAuditUtil.createUserAudit(Constants.AUDIT_EUSER_REGISTER_SAVE, "",""));
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
        userAuditRepository.save(UserAuditUtil.createUserAudit(Constants.AUDIT_EUSER_REGISTRATION_GET, "",""));
        return authCode;
    }
}
