package game.poc.controller;

import game.poc.entity.UserInventory;
import game.poc.service.reporting.ReportingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/unordinal/reports")
@RequiredArgsConstructor
public class ReportingController {

    private final ReportingService reportingService;

    @GetMapping("/fetchActiveUsers")
    public List<String> fetchActiveUsers(){
        return reportingService.numActiveUsers();
    }

    @GetMapping("/fetchAllRegisteredUsers")
    public List<String> fetchRegisteredUsers(){
        return reportingService.numTotalUsers();
    }

    @GetMapping("/fetchNumberOfLogin")
    public Map<String,Integer> fetchNumberOfLogin(){
        return reportingService.numberOfLogins();
    }

    @GetMapping("/fetchAllUsersInventory")
    public List<UserInventory> fetchAllUsersInventory(){
        return reportingService.usersInventory();
    }
}
