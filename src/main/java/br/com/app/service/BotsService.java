package br.com.app.service;

import java.util.List;
import java.util.UUID;

import br.com.app.model.Bots;

/**
 * Interface BotsService
 * 
 * @author Danilo Humberto
 *
 */

public interface BotsService {

	public List<Bots> buscarTodos(String... sorting);

	public Bots buscarPorId(UUID id);

	public void salvar(Bots entity);

	public void salvarTodos(List<Bots> entities);

	public void excluir(Bots entity);

	public void excluirItens(List<Bots> entities);

	public void atualizar(Bots entity);

	public void excluirTodos();
}
