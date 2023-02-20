package game.poc.service.reporting;

import game.poc.entity.UserInventory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ReportingService {
    List<String> numActiveUsers();
    List<String> numTotalUsers();
    List<UserInventory> usersInventory();
    Map<String, Integer> numberOfLogins();
}
