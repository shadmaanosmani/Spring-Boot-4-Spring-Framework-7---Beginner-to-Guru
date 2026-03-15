package guru.springframework.spring_di.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({ "qa", "EN" })
@SpringBootTest
class EnvControllerTestsQa {

	@Autowired
	EnvController envController;

	@Test
	void getEnv() {

		System.out.println(envController.getEnv());

	}

}
