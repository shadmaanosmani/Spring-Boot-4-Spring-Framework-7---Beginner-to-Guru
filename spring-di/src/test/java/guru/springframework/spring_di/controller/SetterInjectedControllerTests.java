package guru.springframework.spring_di.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import guru.springframework.spring_di.service.impl.GreetingServiceImpl;

@SpringBootTest
class SetterInjectedControllerTests {

	SetterInjectedController setterInjectedController;

	@BeforeEach
	void setUp() {

		setterInjectedController = new SetterInjectedController();
		setterInjectedController.setGreetingService(new GreetingServiceImpl());

	}

	@Test
	void sayHello() {

		System.out.println(setterInjectedController.sayHello());

	}

}
