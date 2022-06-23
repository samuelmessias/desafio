package com.samuelmessias.desafio.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samuelmessias.desafio.dto.CidadeDTO;
import com.samuelmessias.desafio.dto.ClienteDTO;
import com.samuelmessias.desafio.entities.enums.Sexo;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ClienteResourceTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private Long existingId;
	private Long nonExistingId;
	
	@BeforeEach
	void setUp() throws Exception {		
		existingId = 1L;		
		nonExistingId = 100L;
		
	}
	
	@Test
	public void findAllShouldReturnPagedResources() throws Exception {
		
		ResultActions result =
				mockMvc.perform(get("/clientes")
					.contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.content").exists());
	}
	
	@Test
	public void findByIdShouldReturnElementWhenExistingId()  throws Exception {
		
		ResultActions result = mockMvc.perform(get("/clientes/{id}", existingId)				
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.nome").value("Thiago"));
	}
	
	@Test
	public void insertShouldInsertWhenUserLogged() throws Exception {
				
		LocalDate dataNasc = LocalDate.of(2001, 10, 5);
		CidadeDTO cidade = new CidadeDTO(1L,"",null);
		
		ClienteDTO dto = new ClienteDTO(null, "Ana Paulo", Sexo.FEMININO, dataNasc, cidade);
		String jsonBody = objectMapper.writeValueAsString(dto);
		
		ResultActions result =
				mockMvc.perform(post("/clientes")
					
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isCreated());
		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(jsonPath("$.nome").value("Ana Paulo"));
			
	}
	
	
	@Test
	public void updateShouldReturn200WhenIdExist() throws Exception {
		
		LocalDate dataNasc = LocalDate.of(2010, 7, 13);
		CidadeDTO cidade = new CidadeDTO(1L,"",null);
		
		ClienteDTO dto = new ClienteDTO(null, "Thiago Alves", Sexo.MASCULINO, dataNasc, cidade);
		String jsonBody = objectMapper.writeValueAsString(dto);
		
		ResultActions result =
				mockMvc.perform(put("/clientes/{id}", existingId)
					
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.nome").value("Thiago Alves"));
	}
	
	
	@Test
	public void updateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
		
		LocalDate dataNasc = LocalDate.of(2010, 7, 13);
		CidadeDTO cidade = new CidadeDTO(1L,"",null);
		
		ClienteDTO dto = new ClienteDTO(null, "Thiago Alves", Sexo.MASCULINO, dataNasc, cidade);
		String jsonBody = objectMapper.writeValueAsString(dto);
		
		ResultActions result =
				mockMvc.perform(put("/clientes/{id}", nonExistingId)					
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isNotFound());
	}
		
	
	@Test
	public void deleteShouldReturnNoContentWhenIdExists() throws Exception {		
		
		ResultActions result = 
				mockMvc.perform(delete("/clientes/{id}", existingId)					
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNoContent());
	}
	
	@Test
	public void deleteShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
		
		ResultActions result = 
				mockMvc.perform(delete("/clientes/{id}", nonExistingId)					
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
	}
	
	

}
