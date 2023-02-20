package game.poc.service.reporting;

import game.poc.entity.ExternalAuth;
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
                        .filter(user -> Instant.now().until(Instant.ofEpochSecond(Long.parseLong(Objects.requireNonNull(JwtToken.getTokenParam(user.getSessionId(), "exp")))), ChronoUnit.SECONDS) > 0)
                        .collect(Collectors.toList());

        List<UserLogin> userLoginList =
                userLoginRepository
                        .findAll()
                        .stream()
                        .filter(user -> Instant.now().until(Instant.ofEpochSecond(Long.parseLong(Objects.requireNonNull(JwtToken.getTokenParam(user.getUserSessionID(), "exp")))), ChronoUnit.SECONDS) > 0)
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

    public Map<String, Integer> numberOfLogins(){

        List<String> userLoginList = userAuditRepository.findByUserActions();
        Map<String, Integer> userMap = new HashMap<>();

        for(String str: userLoginList){
            String[] a = str.split(",");
            userMap.put(a[0], Integer.parseInt(a[1]));
        }
        return userMap;
    }
}
