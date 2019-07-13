package br.com.app.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.app.model.Messages;

/**
 * Interface da classe MessagesRepository
 * 
 * @author Danilo Humberto
 *
 */
@Repository
public interface MessagesRepository extends JpaRepository<Messages, UUID> {

	List<Messages> findByConversationId(UUID conversationId);
}
