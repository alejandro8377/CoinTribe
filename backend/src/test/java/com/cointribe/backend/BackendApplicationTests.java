package com.cointribe.backend;

import com.cointribe.backend.models.User;
import com.cointribe.backend.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class BackendApplicationTests {

	private final MockMvc mockMvc;
	private final UserRepository userRepository;

	@Autowired
	BackendApplicationTests(MockMvc mockMvc, UserRepository userRepository) {
		this.mockMvc = mockMvc;
		this.userRepository = userRepository;
	}

	@Test
	void contextLoads() {
	}

	@Test
	void exposesOpenApiDocumentationForRestEndpoints() throws Exception {
		mockMvc.perform(get("/v3/api-docs"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("/api/users/register")))
				.andExpect(content().string(containsString("Registrar usuario")));
	}

	@Test
	void servesInteractiveSwaggerUi() throws Exception {
		mockMvc.perform(get("/swagger-ui/index.html"))
				.andExpect(status().isOk());
	}

	@Test
	void validatesRequestDtosAtTheHttpBoundary() throws Exception {
		mockMvc.perform(post("/api/users/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{"nombre":"","email":"invalid","password":"123"}
								"""))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.details.nombre").exists())
				.andExpect(jsonPath("$.details.email").exists())
				.andExpect(jsonPath("$.details.password").exists());
	}

	@Test
	void appliesCreateValidationGroupsToPosts() throws Exception {
		mockMvc.perform(post("/api/posts")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{}"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.details.autor").exists())
				.andExpect(jsonPath("$.details.titulo").exists())
				.andExpect(jsonPath("$.details.cuerpo").exists());
	}

	@Test
	void appliesCreateValidationGroupsToAchievements() throws Exception {
		mockMvc.perform(post("/api/achievements")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{}"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.details.codigo").exists())
				.andExpect(jsonPath("$.details.nombre").exists());
	}

	@Test
	void returnsNotFoundForMissingDomainResources() throws Exception {
		mockMvc.perform(put("/api/users/9223372036854775807/profile")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{}"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.error").value("Usuario no encontrado."));
	}

	@Test
	void persistsAuditingTimestampsForEntities() {
		User seededUser = userRepository.findByEmail("admin@cointribe.com").orElseThrow();

		assertNotNull(seededUser.getCreatedAt());
		assertNotNull(seededUser.getUpdatedAt());
	}
}
