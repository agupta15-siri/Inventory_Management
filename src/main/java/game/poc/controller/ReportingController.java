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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/unordinal/reports")
@RequiredArgsConstructor
public class ReportingController {

    private final ReportingService reportingService;

    @GetMapping("/")
    public String reportView(Model model){

        List<String> activeUsers = fetchActiveUsers();
        List<String> registeredUsers = fetchRegisteredUsers();
        List<LoginCount> loginCountList = fetchNumberOfLogin();
        List<UserInventory> usersInventory = fetchAllUsersInventory();


        List<Report> reportList = new ArrayList<>();
        int i=1;
        for (String user: registeredUsers){

            Report r = new Report();

            r.setId(i); i++;
            r.setUserEmail(user);
            r.setActive(activeUsers.contains(user));

            r.setNumOfLogin(loginCountList.stream().filter(lUser -> lUser.getUserEmail().equalsIgnoreCase(user)).map(LoginCount::getLoginCount).findFirst().orElse(0));

            UserInventory userInv = usersInventory.stream().filter(iUser -> iUser.getUserEmail().equalsIgnoreCase(user)).findFirst().orElse(null);
            if (userInv != null){
                r.setAssetName(userInv.getAssetName());
                r.setAssetCount(userInv.getTotalAsset());
            } else {
                r.setAssetName("Fruit");
                r.setAssetCount(0);
            }

            reportList.add(r);
        }

        model.addAttribute("activeUsers", activeUsers.size());
        model.addAttribute("registeredUsers", registeredUsers.size());
        model.addAttribute("numOfLogin", loginCountList.stream().mapToInt(LoginCount::getLoginCount).sum());
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
