package game.poc.repository;

import game.poc.entity.Asset;
import game.poc.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetsRepository extends JpaRepository<Asset, Long>{

}
