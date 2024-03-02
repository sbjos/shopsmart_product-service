package com.shopsmart.productservice;

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
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	private ObjectMapper objectMapper;
	@Container
	private static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.6");

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

//	@Test
//	void contextLoads() {
//	}

	// Integration Test
	@Test
	void createProduct()  {
        String ProductRequest = null;
        try {
            ProductRequest = objectMapper.writeValueAsString(getProductRequest());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(ProductRequest))
					.andExpect(status().isCreated());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

	private ProductRequest getProductRequest() {
		return ProductRequest.builder()
				.name("Test Name")
				.description("This is a test")
				.price(BigDecimal.valueOf(15.89))
				.build();
	}
}
