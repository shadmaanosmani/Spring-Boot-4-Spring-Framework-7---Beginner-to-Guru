package guru.springframework.spring_di.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import guru.springframework.spring_di.service.impl.GreetingServiceImpl;

@SpringBootTest
class PropertyInjectedControllerTests {

	PropertyInjectedController propertyInjectedController;

	@BeforeEach
	void setUp() {

		propertyInjectedController = new PropertyInjectedController();
		propertyInjectedController.greetingService = new GreetingServiceImpl();

	}

	@Test
	void sayHello() {

		System.out.println(propertyInjectedController.sayHello());

	}

}
