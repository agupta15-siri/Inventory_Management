package game.poc.repository;

import game.poc.entity.ExternalAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExternalAuthRepository extends JpaRepository<ExternalAuth, Long>{

    Optional<ExternalAuth> findByEmail(String email);
}
