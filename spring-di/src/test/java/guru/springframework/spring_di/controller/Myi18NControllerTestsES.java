package guru.springframework.spring_di.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({ "ES", "dev" })
@SpringBootTest
class Myi18NControllerTestsES {

	@Autowired
	Myi18NController myi18nController;

	@Test
	void sayHello() {

		System.out.println(myi18nController.sayHello());

	}

}
