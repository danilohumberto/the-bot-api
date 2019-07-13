package br.com.app.service;

import java.util.List;
import java.util.UUID;

import br.com.app.model.Messages;

/**
 * Interface MessagesService
 * 
 * @author Danilo Humberto
 *
 */

public interface MessagesService {

	public Messages buscarPorId(UUID id);

	public List<Messages> buscarPorConversationId(UUID conversationId);

	public void salvar(Messages entity);

}
