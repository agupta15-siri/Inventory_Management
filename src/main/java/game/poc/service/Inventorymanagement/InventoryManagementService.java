package game.poc.service.Inventorymanagement;

import game.poc.entity.Asset;
import game.poc.entity.Mapping;
import game.poc.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface InventoryManagementService {

    Mapping updateInventory(Long userId ,String inventoryName,String inventoryCounts);
    Mapping fetchUserInventory(Long userId);
    List<Mapping> fetchAllUserInventory();
    ResponseEntity<User> deleteInventory(Long userId);
    User addUser(long id);
    List<Asset> getAllAssets();
    Asset addAssets(Asset asset);
}
