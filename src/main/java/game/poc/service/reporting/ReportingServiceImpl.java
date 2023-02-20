package game.poc.service.reporting;

import game.poc.entity.ExternalAuth;
import game.poc.entity.LoginCount;
import game.poc.entity.UserInventory;
import game.poc.entity.UserLogin;
import game.poc.repository.ExternalAuthRepository;
import game.poc.repository.UserAuditRepository;
import game.poc.repository.UserInventoryRepository;
import game.poc.repository.UserLoginRepository;
import game.poc.utility.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportingServiceImpl implements ReportingService{

    private final ExternalAuthRepository externalAuthRepository;
    private final UserLoginRepository userLoginRepository;
    private final UserInventoryRepository userInventoryRepository;
    private final UserAuditRepository userAuditRepository;

    @Override
    public List<String> numActiveUsers() {

        List<ExternalAuth> externalActiveUserList =
                externalAuthRepository
                        .findAll()
                        .stream()
                        .filter(user -> {

                            String expiryTime = JwtToken.getTokenParam(user.getSessionId(), "exp");
                            if (expiryTime != null){
                                return Instant.now().until(Instant.ofEpochSecond(Long.parseLong(expiryTime)), ChronoUnit.SECONDS) > 0;
                            } else {
                                return false;
                            }
                        })
                        .collect(Collectors.toList());

        List<UserLogin> userLoginList =
                userLoginRepository
                        .findAll()
                        .stream()
                        .filter(user -> {

                            if (user.getUserSessionID() != null){

                                String expiryTime = JwtToken.getTokenParam(user.getUserSessionID(), "exp");
                                if (expiryTime != null) {
                                    return Instant.now().until(Instant.ofEpochSecond(Long.parseLong(expiryTime)), ChronoUnit.SECONDS) > 0;
                                } else {
                                    return false;
                                }
                            } else {
                                return false;
                            }
                        })
                        .collect(Collectors.toList());

        List<String> activeUserList = userLoginList.stream().map(UserLogin::getEmail).collect(Collectors.toList());
        activeUserList.addAll(externalActiveUserList.stream().map(ExternalAuth::getEmail).collect(Collectors.toList()));

        return activeUserList;
    }

    @Override
    public List<String> numTotalUsers() {

        List<String> totalUsersList = userLoginRepository.findAll().stream().map(UserLogin::getEmail).collect(Collectors.toList());
        totalUsersList.addAll(externalAuthRepository .findAll().stream().map(ExternalAuth::getEmail).collect(Collectors.toList()));

        return totalUsersList;
    }

    @Override
    public List<UserInventory> usersInventory(){
        return userInventoryRepository.findAll();
    }

    public List<LoginCount> numberOfLogins(){

        List<String> userLoginList = userAuditRepository.findByUserActions();
        List<LoginCount> loginCountList = new ArrayList<>();

        for(String str: userLoginList){

            String[] a = str.split(",");

            LoginCount l = new LoginCount();
            l.setUserEmail(a[0]);
            l.setLoginCount(Integer.parseInt( a[1]));

            loginCountList.add(l);
        }
        return loginCountList;
    }
}
