package br.com.fiap.metaflow.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fiap.metaflow.dto.UsuarioRequest;
import jakarta.persistence.EntityManager;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UsuarioControllerIT {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private EntityManager entityManager;

  private UsuarioRequest usuarioRequestValido;

  @BeforeEach
  void setUp() {
    usuarioRequestValido = new UsuarioRequest(
        "Nome Teste",
        "teste@email.com",
        "Engenheiro de Software",
        "Alcançar senioridade");
    entityManager.createNativeQuery("DELETE FROM [dbo].[mf_usuarios]").executeUpdate();
    entityManager.flush();
  }

  private Long createTestUserAndGetId() throws Exception {
    MvcResult result = mockMvc.perform(post("/usuarios")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(usuarioRequestValido)))
        .andExpect(status().isCreated())
        .andReturn();
    String content = result.getResponse().getContentAsString();
    return objectMapper.readTree(content).get("id").asLong();
  }

  @Test
  void testCreateUsuario() throws Exception {
    mockMvc.perform(post("/usuarios")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(usuarioRequestValido)))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.nome", is("Nome Teste")))
        .andExpect(jsonPath("$.email", is("teste@email.com")));
  }

  @Test
  void testReadUsuariosVazio() throws Exception {
    mockMvc.perform(get("/usuarios")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(0)));
  }

  @Test
  void testReadUsuarioExistente() throws Exception {
    Long userId = createTestUserAndGetId();

    mockMvc.perform(get("/usuarios/{id}", userId)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(userId.intValue())))
        .andExpect(jsonPath("$.email", is("teste@email.com")));
  }

  @Test
  void testUpdateUsuario() throws Exception {
    Long userId = createTestUserAndGetId();

    UsuarioRequest usuarioRequestAtualizado = new UsuarioRequest(
        "Nome Atualizado",
        "atualizado@email.com",
        "Tech Lead",
        "Gerenciar equipe");

    mockMvc.perform(put("/usuarios/{id}", userId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(usuarioRequestAtualizado)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(userId.intValue())))
        .andExpect(jsonPath("$.nome", is("Nome Atualizado")))
        .andExpect(jsonPath("$.tituloProfissional", is("Tech Lead")));
  }

  @Test
  void testDeleteUsuario() throws Exception {
    Long userId = createTestUserAndGetId();

    mockMvc.perform(delete("/usuarios/{id}", userId)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());

    mockMvc.perform(get("/usuarios/{id}", userId))
        .andExpect(status().isNotFound());
  }
}