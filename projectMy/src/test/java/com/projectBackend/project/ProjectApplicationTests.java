package com.projectBackend.project;

import com.projectBackend.project.entity.Token;
import com.projectBackend.project.repository.TokenRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ProjectApplicationTests {
	@Autowired
	private TokenRepository tokenRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void findALl() {
		List<Token> tokens = tokenRepository.findAll();

	}

}
