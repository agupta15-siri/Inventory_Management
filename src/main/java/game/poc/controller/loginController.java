package game.poc.controller;

import game.poc.entity.Asset;
import game.poc.entity.Mapping;
import game.poc.entity.User;
import game.poc.entity.UserLogin;
import game.poc.service.Inventorymanagement.InventoryManagementService;
import game.poc.service.userlogin.UserLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/unordinal/users")
@RequiredArgsConstructor
public class loginController {

    private final UserLoginService userLoginService;

    @GetMapping("/")
    public void checkHealth() {
        ResponseEntity.ok();
    }

    @GetMapping("/fetchAllUserDetail")
    public List<UserLogin> getAllUser() {
        return userLoginService.getUsers();
    }

    @GetMapping("/fetchUser/{emailId}")
    public UserLogin getUser(@PathVariable(value = "emailId") String emailId) {
        return userLoginService.getSpecificUser(emailId);
    }


    @PostMapping("/userRegistration")
    public UserLogin userRegistration(@RequestBody UserLogin userLoginRequest) {
        return userLoginService.userRegistration(userLoginRequest);
    }

    @PostMapping("/login")
    public UserLogin userAuthentication(@RequestBody UserLogin userLoginRequest) {
        return userLoginService.userAuthentication(userLoginRequest);
    }


    @CrossOrigin(value = "*")
    @RequestMapping(value = "/health", method = RequestMethod.GET)
    public ResponseEntity<?> health() throws Exception {
        return ResponseEntity.status(200).body("Ok");
    }
}
