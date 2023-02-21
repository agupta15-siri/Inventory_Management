package game.poc.service.reporting;

import game.poc.entity.LoginCount;
import game.poc.entity.UserInventory;
import game.poc.model.Report;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface ReportingService {
    List<String> numActiveUsers();
    List<String> numTotalUsers();
    List<UserInventory> usersInventory();
    List<LoginCount> numberOfLogins();
    List<Report> reportingInfo();
}
