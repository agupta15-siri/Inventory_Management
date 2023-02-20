package game.poc.service.Inventorymanagement;

import game.poc.entity.Asset;
import game.poc.entity.Mapping;
import game.poc.entity.User;
import game.poc.entity.UserInventory;
import game.poc.repository.*;
import game.poc.utility.Constants;
import game.poc.utility.JwtToken;
import game.poc.utility.UserAuditUtil;
import lombok.RequiredArgsConstructor;
import game.poc.exception.ResourceNotFoundException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryManagementServiceImpl implements InventoryManagementService {

    private final UserRepository userRepository;
    private final MappingRepository mappingRepository;
    private final AssetsRepository assetsRepository;
    private final DelayRepository delayRepository;
    private final UserInventoryRepository userInventoryRepository;
    private final UserAuditRepository userAuditRepository;

    @Override
    public Mapping updateInventory(Long userId, String inventoryName, String counts) {
        delay();
        if (StringUtils.isEmpty(inventoryName.trim()) || StringUtils.isEmpty(counts.trim()) || Objects.isNull(userId)) {
            throw new ResourceNotFoundException("Either UserID,inventoryName,inventory counts input are wrong or incorrect format");
        }
        Optional<Mapping> mappedUser = this.mappingRepository.findAll().stream().filter(mapping -> (mapping.getMappingId() == userId && mapping.getAssetName().contains(inventoryName))).findFirst();
        if (!mappedUser.isPresent())
            throw new ResourceNotFoundException("User not found with id :" + userId);
        Mapping mapping = mappedUser.get();
        if (!StringUtils.isEmpty(mapping.getAssetName()) && mapping.getAssetName().contains(inventoryName) && Objects.nonNull(mapping.getTotalAsset())) {
            mapping.setTotalAsset(mapping.getTotalAsset() + Integer.parseInt(counts));
        }
        return this.mappingRepository.save(mapping);
    }

    @Override
    public Mapping fetchUserInventory(Long userId) {
        delay();
        Optional<Mapping> mappedUser = this.mappingRepository.findAll().stream().filter(mapping -> mapping.getMappingId() == userId).findFirst();
        if (!mappedUser.isPresent())
            throw new ResourceNotFoundException("User not found with id :" + userId);
        return mappedUser.get();


    }
    @Override
    public UserInventory updateUserInventory(String sessionId, String assetName, String updateBy){

        String userEmail = getTokenParam(sessionId, "email");

        UserInventory userInventory = userInventoryRepository
                .findByUserEmail(userEmail)
                .orElse(null);

        if (userInventory == null){
            userInventory = addUserInventory(userEmail);
        }

        userInventory.setTotalAsset(userInventory.getTotalAsset() + Integer.parseInt(updateBy));

        userAuditRepository.save(UserAuditUtil.createUserAudit(Constants.AUDIT_UPDATE_INVENTORY, userEmail, JwtToken.getTokenParam(sessionId,"jti")));

        return userInventoryRepository.save(userInventory);
    }

    @Override
    public UserInventory fetchUserInventory(String sessionId) {

        String userEmail = getTokenParam(sessionId, "email");

        UserInventory userInventory = userInventoryRepository
                .findByUserEmail(userEmail)
                .orElse(null);

        userAuditRepository.save(UserAuditUtil.createUserAudit(Constants.AUDIT_FETCH_INVENTORY, userEmail, JwtToken.getTokenParam(sessionId,"jti")));

        if (userInventory == null){
            return addUserInventory(userEmail);
        }
        return userInventory;
    }

    @Override
    public List<Mapping> fetchAllUserInventory() {
        delay();
        return this.mappingRepository.findAll();
    }

    @Override
    public List<Asset> getAllAssets() {
        delay();
        return this.assetsRepository.findAll();
    }

    @Override
    public Asset addAssets(Asset asset) {
        delay();
        if (Objects.nonNull(asset)) {
            if (StringUtils.isEmpty(String.valueOf(asset.getAssignedAssetCount())) && StringUtils.isEmpty(asset.getAssetName())) {
                this.assetsRepository.save(asset);
            }
        }
        return Asset.builder().build();
    }

    @Override
    public ResponseEntity<User> deleteInventory(Long userId) {
        delay();
        User existingUser = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + userId));
        this.userRepository.delete(existingUser);
        return ResponseEntity.ok().build();
    }

    @Override
    public User addUser(long id) {
        delay();
        Optional<User> mappedUser = this.userRepository.findAll().stream().filter(userId -> userId.getUserId() == id).findFirst();
        User user;
        if (mappedUser.isPresent()) {
            return mappedUser.get();
        } else {
            user = new User();
            user.setUserId(id);
            user.setUserName("ABC".concat(String.valueOf(id)));
            this.userRepository.save(user);
            Mapping mapping = new Mapping();
            mapping.setMappingId(id);
            mapping.setAssetName("Fruit");
            mapping.setTotalAsset(0);
            this.mappingRepository.save(mapping);
        }
        return user;
    }

    private void delay(){
        try {
            Thread.sleep(Long.parseLong(delayRepository.findById(1L).get().getDelayTime()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getTokenParam(String token, String param) {

        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));

        try {
            JSONObject jsonObject = new JSONObject(payload);
            return jsonObject.getString(param);

        } catch (JSONException ex){
            ex.printStackTrace();
        }
        return null;
    }
    public UserInventory addUserInventory(String userEmail){


            UserInventory userInventory = new UserInventory();

            userInventory.setUserEmail(userEmail);
            userInventory.setTotalAsset(0);
            userInventory.setAssetName("Fruit");

            return userInventoryRepository.save(userInventory);

    }

}
