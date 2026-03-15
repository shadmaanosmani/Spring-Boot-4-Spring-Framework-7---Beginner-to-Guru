package guru.springframework.spring_di.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EnvControllerTests {

	@Autowired
	EnvController envController;

	@Test
	void getEnv() {

		System.out.println(envController.getEnv());

	}

}
