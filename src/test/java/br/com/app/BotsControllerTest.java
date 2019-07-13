package br.com.app;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.app.controller.BotsController;
import br.com.app.model.Bots;
import br.com.app.service.BotsService;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class BotsControllerTest {
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@MockBean
	private BotsService botsServiceMock;

	Bots str1, str2;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();

		str1 = new Bots();
		str2 = new Bots();

		str1.setName("Iolanda");
		str1.setId(UUID.randomUUID());

		str1.setName("Benito de Paula");
		str1.setId(UUID.randomUUID());
	}

	@Test
	public void givenContext_whenServletContext_thenItProvidesController() {
		ServletContext servletContext = context.getServletContext();

		assertNotNull(servletContext);
		assertTrue(servletContext instanceof MockServletContext);
		assertNotNull(context.getBeansOfType(BotsController.class));
		assertNotNull(this.botsServiceMock);
	}

	private void checkAtributesBots(ResultActions resultActions, Boolean isArray) throws Exception {
		String jsonIndex = isArray ? "$[0]." : "$.";

		resultActions.andExpect(jsonPath(jsonIndex + "id", containsString(str1.getId().toString())))
				.andExpect(jsonPath(jsonIndex + "name", is(str1.getName())));
	}

	@Test
	public void buscarTodos() throws Exception {
		when(botsServiceMock.buscarTodos()).thenReturn(Arrays.asList(str1, str2));

		ResultActions resultActions = this.mockMvc
				.perform(get("/bots").accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));

		this.checkAtributesBots(resultActions, true);

		verify(botsServiceMock, times(1)).buscarTodos();
		verifyNoMoreInteractions(botsServiceMock);
	}

	@Test
	public void buscarTodosNaoEncontrado() throws Exception {
		when(botsServiceMock.buscarTodos()).thenReturn(new ArrayList<Bots>());

		this.mockMvc.perform(get("/bots").accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andDo(print())
				.andExpect(content().string(containsString("Nenhum registro encontrado")))
				.andExpect(status().isNoContent());

		verify(botsServiceMock, times(1)).buscarTodos();
		verifyNoMoreInteractions(botsServiceMock);
	}

	@Test
	public void buscarTodosException() throws Exception {
		when(botsServiceMock.buscarTodos()).thenThrow(NullPointerException.class);

		this.mockMvc.perform(get("/bots").accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andDo(print())
				.andExpect(status().isBadRequest());

		verify(botsServiceMock, times(1)).buscarTodos();
		verifyNoMoreInteractions(botsServiceMock);
	}

	@Test
	public void buscarPorId() throws Exception {
		when(botsServiceMock.buscarPorId(str1.getId())).thenReturn(str1);

		ResultActions resultActions = this.mockMvc.perform(get("/bots/{id}", str1.getId())
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andDo(print()).andExpect(status().isOk());

		this.checkAtributesBots(resultActions, false);

		verify(botsServiceMock, times(1)).buscarPorId(str1.getId());
		verifyNoMoreInteractions(botsServiceMock);
	}

	@Test
	public void buscarPorIdNaoEncontrado() throws Exception {
		when(botsServiceMock.buscarPorId(str1.getId())).thenReturn(null);

		this.mockMvc
				.perform(get("/bots/{id}", str1.getId().toString())
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print()).andExpect(content().string(containsString(" não encontrado")))
				.andExpect(status().isNotFound());

		verify(botsServiceMock, times(1)).buscarPorId(str1.getId());
		verifyNoMoreInteractions(botsServiceMock);
	}

	@Test
	public void buscarPorIdException() throws Exception {
		when(botsServiceMock.buscarPorId(str1.getId())).thenThrow(NullPointerException.class);

		this.mockMvc
				.perform(get("/bots/{id}", (str1.getId()))
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print()).andExpect(status().isBadRequest());

		verify(botsServiceMock, times(1)).buscarPorId(str1.getId());
		verifyNoMoreInteractions(botsServiceMock);
	}

	@Test
	public void atualizar() throws Exception {
		when(botsServiceMock.buscarPorId(str1.getId())).thenReturn(str1);

		this.mockMvc
				.perform(
						put("/bots/{id}", str1.getId()).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
								.content(new ObjectMapper().writeValueAsString(str1)))
				.andDo(print()).andExpect(status().isOk());

		verify(botsServiceMock, times(1)).buscarPorId(str1.getId());
		verify(botsServiceMock, times(1)).atualizar(str1);
		verifyNoMoreInteractions(botsServiceMock);
	}

	@Test
	public void atualizarIdNaoEncontrado() throws Exception {
		when(botsServiceMock.buscarPorId(str1.getId())).thenReturn(null);

		this.mockMvc
				.perform(
						put("/bots/{id}", str1.getId()).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
								.content(new ObjectMapper().writeValueAsString(str1)))
				.andDo(print()).andExpect(content().string(containsString("Não foi possível atualizar. O id")))
				.andExpect(status().isNotFound());

		verify(botsServiceMock, times(1)).buscarPorId(str1.getId());
		verifyNoMoreInteractions(botsServiceMock);
	}

	@Test
	public void atualizarException() throws Exception {
		when(botsServiceMock.buscarPorId(str1.getId())).thenReturn(str1);

		doThrow(NullPointerException.class).when(botsServiceMock).atualizar(str1);

		this.mockMvc
				.perform(
						put("/bots/{id}", str1.getId()).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
								.content(new ObjectMapper().writeValueAsString(str1)))
				.andDo(print()).andExpect(status().isBadRequest());

		verify(botsServiceMock, times(1)).buscarPorId(str1.getId());
		verify(botsServiceMock, times(1)).atualizar(str1);
		verifyNoMoreInteractions(botsServiceMock);
	}

	@Test
	public void criar() throws Exception {
		this.mockMvc
				.perform(post("/bots/").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
						.content(new ObjectMapper().writeValueAsString(str1)))
				.andDo(print()).andExpect(status().isOk());

		verify(botsServiceMock, times(1)).salvar(str1);
		verifyNoMoreInteractions(botsServiceMock);
	}

	@Test
	public void criarException() throws Exception {
		doThrow(NullPointerException.class).when(botsServiceMock).salvar(str1);

		this.mockMvc
				.perform(post("/bots/").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
						.content(new ObjectMapper().writeValueAsString(str1)))
				.andDo(print()).andExpect(status().isBadRequest());

		verify(botsServiceMock, times(1)).salvar(str1);
		verifyNoMoreInteractions(botsServiceMock);
	}
	
	@Test
	public void excluirPorId() throws Exception {
		when(botsServiceMock.buscarPorId(str1.getId())).thenReturn(str1);

		this.mockMvc.perform(
				delete("/bots/{id}", str1.getId()).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print()).andExpect(status().isOk());

		verify(botsServiceMock, times(1)).buscarPorId(str1.getId());
		verify(botsServiceMock, times(1)).excluir(str1);
		verifyNoMoreInteractions(botsServiceMock);
	}

	@Test
	public void excluirPorIdException() throws Exception {
		when(botsServiceMock.buscarPorId(str1.getId())).thenReturn(str1);

		doThrow(NullPointerException.class).when(botsServiceMock).excluir(str1);

		this.mockMvc
				.perform(delete("/bots/{id}", str1.getId())
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print()).andExpect(status().isBadRequest());

		verify(botsServiceMock, times(1)).buscarPorId(str1.getId());
		verify(botsServiceMock, times(1)).excluir(str1);
		verifyNoMoreInteractions(botsServiceMock);
	}

	@Test
	public void excluirPorIdIdNaoEncontrado() throws Exception {
		when(botsServiceMock.buscarPorId(str1.getId())).thenReturn(null);

		this.mockMvc
				.perform(delete("/bots/{id}", str1.getId())
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print()).andExpect(content().string(containsString("Não foi possível remover. O id ")))
				.andExpect(status().isNotFound());

		verify(botsServiceMock, times(1)).buscarPorId(str1.getId());
		verifyNoMoreInteractions(botsServiceMock);
	}
	
	@Test
	public void excluirTodos() throws Exception {
		this.mockMvc.perform(delete("/bots/").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print()).andExpect(status().isOk());

		verify(botsServiceMock, times(1)).excluirTodos();
		verifyNoMoreInteractions(botsServiceMock);
	}

	@Test
	public void excluirTodosException() throws Exception {
		doThrow(NullPointerException.class).when(botsServiceMock).excluirTodos();

		this.mockMvc.perform(delete("/bots/").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print()).andExpect(status().isBadRequest());

		verify(botsServiceMock, times(1)).excluirTodos();
		verifyNoMoreInteractions(botsServiceMock);
	}
}
