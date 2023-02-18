package game.poc.controller;

import java.util.List;

import game.poc.entity.Asset;
import game.poc.entity.Mapping;
import game.poc.entity.User;
import game.poc.entity.UserInventory;
import game.poc.service.Inventorymanagement.InventoryManagementService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/unordinal")
@RequiredArgsConstructor
public class UserManagementController {

    private final InventoryManagementService inventoryManagementService;

    @GetMapping("/")
    public void checkHealth() {
        ResponseEntity.ok();
    }

    @GetMapping("/fetchAllUserInventory")
    public List<Mapping> getAllUserInventory() {
        return inventoryManagementService.fetchAllUserInventory();
    }

    @GetMapping("/fetchAllAssets")
    public List<Asset> getAllAssets() {
        return inventoryManagementService.getAllAssets();
    }

    @GetMapping("/fetchUserInventory/{id}")
    public Mapping getUserInventoryById(@PathVariable(value = "id") long userId) {
        return inventoryManagementService.fetchUserInventory(userId);
    }

    @PutMapping("/updateInventory/{userId}&{inventoryName}&{count}")
    public Mapping updateUserInventory(@PathVariable("inventoryName") String inventoryName, @PathVariable("userId") String userId, @PathVariable("count") String count) {
        return inventoryManagementService.updateInventory(Long.parseLong(userId), inventoryName, count);
    }

    @GetMapping("/addUsers/{userID}")
    public User createUser(@PathVariable("userID") long userId) {
        return inventoryManagementService.addUser(userId);
    }

    @PostMapping("/addAssets")
    public Asset addAssets(@RequestBody Asset asset) {
        return inventoryManagementService.addAssets(asset);
    }

    @DeleteMapping("/deleteInventory/{id}")
    public ResponseEntity<User> deleteInventory(@PathVariable("id") long userId) {
        return inventoryManagementService.deleteInventory(userId);
    }

    @CrossOrigin(value = "*")
    @RequestMapping(value = "/health", method = RequestMethod.GET)
    public ResponseEntity<?> health() throws Exception {
        return ResponseEntity.status(200).body("Ok");
    }


    @GetMapping("/fetchUserInventory")
    public UserInventory getUserInventoryFromSession(@RequestHeader String userSessionID) {
        return inventoryManagementService.fetchUserInventory(userSessionID);
    }

    @PutMapping("/updateInventory/{assetName}&{updateAssetBy}")
    public UserInventory updateUserInventoryFromSession(@RequestHeader String userSessionID, @PathVariable("assetName") String assetName, @PathVariable("updateAssetBy") String updateBy) {
        return inventoryManagementService.updateUserInventory(userSessionID, assetName, updateBy);
    }
}
