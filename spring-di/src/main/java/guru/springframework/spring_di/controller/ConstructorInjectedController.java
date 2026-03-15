package guru.springframework.spring_di.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import guru.springframework.spring_di.service.GreetingService;

@Controller
public class ConstructorInjectedController {

	private final GreetingService greetingService;

	public ConstructorInjectedController(@Qualifier("greetingServiceImpl") GreetingService greetingService) {

		this.greetingService = greetingService;

	}

	public String sayHello() {

		return greetingService.getGreeting();

	}

}
