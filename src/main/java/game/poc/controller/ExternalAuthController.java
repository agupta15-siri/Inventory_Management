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

    @PostMapping("/saveAuthToken")
    public ResponseEntity<String> saveAuthToken(@RequestBody ExternalAuth externalAuth) {
        externalAuth.setSessionId(jwtToken.generateToken(externalAuth.getLocalId(),externalAuth.getEmail(),null));
        externalAuthService.saveToken(externalAuth);
        return new ResponseEntity<>("SessionID = " + externalAuth.getSessionId(), HttpStatus.CREATED);
    }

    @GetMapping("/getAuthToken")
    public ResponseEntity<String> getAuthToken(@RequestParam String emailId) {
        return new ResponseEntity<>("AuthToken = " +externalAuthService.getAuthToken(emailId), HttpStatus.OK);
    }

}
