package game.poc.service.userlogin;

import game.poc.crypt.RSAEncryptionDecryptionService;
import game.poc.entity.UserLogin;
import game.poc.exception.ResourceNotFoundException;
import game.poc.repository.*;
import game.poc.utility.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserLoginServiceImpl implements UserLoginService {

    private final UserLoginRepository userLoginRepository;
    private final JwtToken jwtToken;
    private final RSAEncryptionDecryptionService rsaEncryptionDecryptionService;

    @Override
    public List<UserLogin> getUsers() {
        return userLoginRepository.findAll();
    }

    @Override
    public UserLogin getSpecificUser(String emailId) {
        Optional<UserLogin> userLogin = userLoginRepository.findByEmail(emailId);
        if (!userLogin.isPresent())
            throw new ResourceNotFoundException("Email ID not found :" + emailId);
        return userLogin.get();
    }

    @Override
    public UserLogin userRegistration(UserLogin userLogin) {
        Optional<UserLogin> userLogin1 = userLoginRepository.findByEmail(userLogin.getEmail());
        if (!userLogin1.isPresent()) {
           /* userLogin.setUserSessionID(jwtToken.generateToken(userLogin.getUserName(), userLogin.getEmail()));*/
            userLogin.setUserName(rsaEncryptionDecryptionService.encryptData(userLogin.getUserName()));
            userLogin.setPassword(rsaEncryptionDecryptionService.encryptData(userLogin.getPassword()));
            userLogin.setAccountID(UUID.randomUUID().toString().substring(0, 9));
            return userLoginRepository.save(userLogin);
        } else throw new ResourceNotFoundException("User already registered with email ID:" + userLogin.getEmail());
    }

    @Override
    public UserLogin userAuthentication(UserLogin userLogin) {
        Optional<UserLogin> optionalUserLogin = userLoginRepository.findByEmail(userLogin.getEmail());
        UserLogin userLoginDB;
        if (optionalUserLogin.isPresent()) {
            userLoginDB = optionalUserLogin.get();
            String userEmailDB = userLoginDB.getEmail();
            String userPasswordDB = rsaEncryptionDecryptionService.decryptData(userLoginDB.getPassword());
            if (userLogin.getEmail().equalsIgnoreCase(userEmailDB) && userLogin.getPassword().equalsIgnoreCase(userPasswordDB)) {
                userLoginDB.setUserSessionID(jwtToken.generateToken(userLogin.getUserName(), userLogin.getEmail(), userLogin.getAccountID()));
                userLoginRepository.save(userLoginDB);
            }else throw new ResourceNotFoundException("Email ID or password is incorrect.");

        } else throw new ResourceNotFoundException("Email ID not found :" + userLogin.getEmail());
        return userLoginDB;
    }
}
