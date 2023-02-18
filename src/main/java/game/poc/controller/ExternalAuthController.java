package game.poc.controller;

import game.poc.entity.ExternalAuth;
import game.poc.entity.UserLogin;
import game.poc.service.externallogin.ExternalAuthService;
import game.poc.service.userlogin.UserLoginService;
import game.poc.utility.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/unordinal/external")
@RequiredArgsConstructor
public class ExternalAuthController {

    private final ExternalAuthService externalAuthService;
    private final JwtToken jwtToken;

    @GetMapping("/")
    public void checkHealth() {
        ResponseEntity.ok();
    }

    @PostMapping("addUserInfo")
    public ResponseEntity<ExternalAuth> addExternalUser(@RequestBody ExternalAuth externalAuth){
        return new ResponseEntity<>(externalAuthService.saveToken(externalAuth), HttpStatus.OK);
    }


    @GetMapping("saveAuthToken")
    public ResponseEntity<String> saveAuthToken(@RequestParam String state, @RequestParam String code, @RequestParam String scope){

        externalAuthService.saveAuthCode(state, code);
        return new ResponseEntity<>("Login successful. Return back to application.", HttpStatus.CREATED);
    }


    @GetMapping("getAuthToken")
    public ResponseEntity<String> getAuthToken(@RequestParam String state){

        int maxWaitTime = 10000;
        int waitTime = 1000;
        int i = 1;
        String authCode = externalAuthService.getAuthCode(state);

        while (authCode == null && (waitTime * i) < maxWaitTime){

            try{
                Thread.sleep(waitTime);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            i++;
            authCode = externalAuthService.getAuthCode(state);
        }
        return new ResponseEntity<>(authCode, HttpStatus.OK);
    }

}
