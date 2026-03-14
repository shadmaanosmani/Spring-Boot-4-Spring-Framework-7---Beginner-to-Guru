package guru.springframework.spring_di.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyControllerTests {

	@Autowired
	MyController myController;

	@Test
	void sayHello() {

		System.out.println(myController.sayHello());

	}

}
