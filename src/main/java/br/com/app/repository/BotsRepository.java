package br.com.app.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.app.model.Bots;

/**
 * Interface da classe BotsRepository
 * 
 * @author Danilo Humberto
 *
 */
@Repository
public interface BotsRepository extends JpaRepository<Bots, UUID> {
}
