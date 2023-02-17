package game.poc.repository;

import game.poc.entity.Asset;
import game.poc.entity.Delay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DelayRepository extends JpaRepository<Delay, Long>{

}
