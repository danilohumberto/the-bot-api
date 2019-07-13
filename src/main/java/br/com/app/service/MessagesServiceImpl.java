package br.com.app.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.app.model.Messages;
import br.com.app.repository.MessagesRepository;

/**
 * Implementacao da classe MessagesService
 * 
 * @author Danilo Humberto
 *
 */
@Service
public class MessagesServiceImpl implements MessagesService {

	@Autowired
	private MessagesRepository repository;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Messages buscarPorId(UUID id) {
		Optional<Messages> ret = repository.findById(id);
		if (!ret.isPresent()) {
			return null;
		}
		return ret.get();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Messages> buscarPorConversationId(UUID conversationId) {
		return repository.findByConversationId(conversationId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void salvar(Messages entity) {
		repository.save(entity);
	}

}
