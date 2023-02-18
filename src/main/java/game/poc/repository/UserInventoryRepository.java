package game.poc.repository;

import game.poc.entity.UserInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserInventoryRepository extends JpaRepository<UserInventory, Long> {

    Optional<UserInventory> findByUserEmail(String email);
}
