package guru.springframework.spring_di.controller;

import org.springframework.stereotype.Controller;

import guru.springframework.spring_di.service.GreetingService;

@Controller
public class ConstructorInjectedController {

	private final GreetingService greetingService;

	public ConstructorInjectedController(GreetingService greetingService) {

		this.greetingService = greetingService;

	}

	public String sayHello() {

		return greetingService.getGreeting();

	}

}
