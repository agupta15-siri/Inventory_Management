package game.poc.controller;

import game.poc.entity.LoginCount;
import game.poc.entity.UserInventory;
import game.poc.model.Report;
import game.poc.service.reporting.ReportingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
@RequestMapping("/unordinal/reports")
@RequiredArgsConstructor
public class ReportingController {

    private final ReportingService reportingService;

    @GetMapping("/")
    public String reportView(Model model){

        List<Report> reportList = reportingService.reportingInfo();

        int activeUserCount = 0;
        int registeredUserCount = reportList.size();
        int totalLogins = 0;

        for (Report r: reportList){

            if (r.isActive()){
                activeUserCount++;
            }
            totalLogins += r.getNumOfLogin();
        }

        model.addAttribute("activeUsers", activeUserCount);
        model.addAttribute("registeredUsers", registeredUserCount);
        model.addAttribute("numOfLogin", totalLogins);
        model.addAttribute("completeList", reportList);

        return "index";
    }

    @GetMapping("/fetchActiveUsers")
    @ResponseBody
    public List<String> fetchActiveUsers(){
        return reportingService.numActiveUsers();
    }

    @GetMapping("/fetchAllRegisteredUsers")
    @ResponseBody
    public List<String> fetchRegisteredUsers(){
        return reportingService.numTotalUsers();
    }

    @GetMapping("/fetchNumberOfLogin")
    @ResponseBody
    public List<LoginCount> fetchNumberOfLogin(){
        return reportingService.numberOfLogins();
    }

    @GetMapping("/fetchAllUsersInventory")
    @ResponseBody
    public List<UserInventory> fetchAllUsersInventory(){
        return reportingService.usersInventory();
    }
}
