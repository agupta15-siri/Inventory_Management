package game.poc.service.Inventorymanagement;

import game.poc.entity.Asset;
import game.poc.entity.Mapping;
import game.poc.entity.User;
import game.poc.entity.UserInventory;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface InventoryManagementService {

    Mapping updateInventory(Long userId ,String inventoryName,String inventoryCounts);
    UserInventory updateUserInventory(String sessionId, String assetName, String updateBy);
    Mapping fetchUserInventory(Long userId);
    UserInventory fetchUserInventory(String sessionId);
    List<Mapping> fetchAllUserInventory();
    ResponseEntity<User> deleteInventory(Long userId);
    User addUser(long id);
    List<Asset> getAllAssets();
    Asset addAssets(Asset asset);
}
