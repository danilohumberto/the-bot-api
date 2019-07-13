package br.com.app.controller;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.app.model.Messages;
import br.com.app.service.MessagesService;
import br.com.app.util.ErroGenerico;
import lombok.Getter;

/**
 * Classe controller que expoe os serviços rest.
 * 
 * @author Danilo Humberto
 *
 */
@RestController
@RequestMapping("/messages")
public class MessagesController {

	/**
	 * logger
	 */
	public static final Logger LOGGER = LoggerFactory.getLogger(MessagesController.class);

	/**
	 * Injeção de dependencia.
	 */
	@Autowired
	@Getter
	private MessagesService service;

	@PostMapping
	public ResponseEntity salvar(@RequestBody Messages entity) {
		try {
			LOGGER.info("Classe: {}, criando registro: {}", getClass().getName(), entity);

			if (!ObjectUtils.isEmpty(entity.getId())) {
				return new ResponseEntity(new ErroGenerico("Não é possível realizar atualizações em mensagens.",
						HttpStatus.UNPROCESSABLE_ENTITY.toString()), HttpStatus.UNPROCESSABLE_ENTITY);
			}

			service.salvar(entity);

			return ResponseEntity.ok(entity);

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "salvar", e);
		}
	}

	/**
	 * Serviço que busca por id um registro da entidade.
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity buscarPorId(@PathVariable("id") UUID id) {
		try {
			LOGGER.info("Classe: {}, buscando com o id {}", getClass().getName(), id);
			Messages entity = service.buscarPorId(id);
			if (entity == null) {
				LOGGER.error("Id {} não encontrado.", id);
				return new ResponseEntity(
						new ErroGenerico("Id " + id + " não encontrado", HttpStatus.NOT_FOUND.toString()),
						HttpStatus.NOT_FOUND);
			}
			return ResponseEntity.ok(entity);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "buscarPorId", e);
		}
	}

	/**
	 * Serviço que busca por conversationId.
	 * 
	 * @param conversationId
	 * @return
	 */
	@GetMapping
	public ResponseEntity buscarPorConversationId(
			@RequestParam(name = "conversationId", required = true) UUID conversationId) {
		LOGGER.info("Classe: {}, buscando com o conversationId {}", getClass().getName(), conversationId);
		try {
			List<Messages> listMessages = service.buscarPorConversationId(conversationId);

			if (ObjectUtils.isEmpty(listMessages)) {
				LOGGER.info("Nenhum registro encontrado");
				return new ResponseEntity(
						new ErroGenerico("Nenhum registro encontrado", HttpStatus.NO_CONTENT.toString()),
						HttpStatus.NO_CONTENT);
			}

			return ResponseEntity.ok(listMessages);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "buscarPorConversationId", e);
		}
	}

}
