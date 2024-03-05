package com.shopsmart.productservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopsmart.productservice.dto.ProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration Testing
 */
@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {
	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.6");
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	static {
		mongoDBContainer.start();
	}

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Test
	void addProduct_successful() throws Exception {
		// GIVEN
		String product = objectMapper.writeValueAsString(
				ProductRequest.builder()
						.name("Test Name")
						.description("This is a test")
						.price(BigDecimal.valueOf(15.89))
						.build()
		);

		// WHEN - THEN
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
						.contentType(MediaType.APPLICATION_JSON)
						.content(product))
				.andExpect(status().isCreated());
	}

	@Test
	void addProduct_notSuccessful() throws Exception {
		// GIVEN
		String product = objectMapper.writeValueAsString(
				ProductRequest.builder()
//						.name("Test Name")
//						.description("This is a test")
//						.price(BigDecimal.valueOf(15.89))
						.build()
		);

		// WHEN - THEN
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
						.contentType(MediaType.APPLICATION_JSON)
						.content(product))
				.andExpect(status().isCreated());
	}
}
