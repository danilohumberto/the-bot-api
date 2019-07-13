package br.com.app.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.app.model.Bots;
import br.com.app.repository.BotsRepository;
import javassist.NotFoundException;

/**
 * Implementacao da classe BotsService
 * 
 * @author Danilo Humberto
 *
 */
@Service
public class BotsServiceImpl implements BotsService {

	@Autowired
	private BotsRepository repository;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Bots> buscarTodos(String... sorting) {
		Sort sort = null;
		if (sorting != null && sorting.length > 0) {
			sort = new Sort(Sort.Direction.ASC, sorting);
			return repository.findAll(sort);
		}

		return repository.findAll();
	}

	/**
	 * Método buscar bots por id
	 * 
	 * @throws NotFoundException
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Bots buscarPorId(UUID id) {
		Optional<Bots> ret = repository.findById(id);
		if (!ret.isPresent()) {
			return null;
		}
		return ret.get();
	}

	/**
	 * Método que salva
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void salvar(Bots entity) {
		repository.save(entity);
	}

	/**
	 * Método que salva todos
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void salvarTodos(List<Bots> entities) {
		repository.saveAll(entities);
	}

	/**
	 * Método que exclui um elemento
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void excluir(Bots entity) {
		repository.delete(entity);
	}

	/**
	 * Método que exclui listagem
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void excluirItens(List<Bots> entities) {
		repository.deleteAll(entities);
	}

	/**
	 * Método que atualiza
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void atualizar(Bots entity) {
		salvar(entity);
	}

	/**
	 * Método que exclui todos bots cadastrados.
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void excluirTodos() {
		repository.deleteAll();
	}
}
