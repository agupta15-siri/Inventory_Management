package game.poc.repository;

import game.poc.entity.ExternalAuth;
import game.poc.entity.ExternalAuthParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExternalAuthDetailsRepository extends JpaRepository<ExternalAuthParam, Long>{
    Optional<ExternalAuthParam> findByState(String state);
}
