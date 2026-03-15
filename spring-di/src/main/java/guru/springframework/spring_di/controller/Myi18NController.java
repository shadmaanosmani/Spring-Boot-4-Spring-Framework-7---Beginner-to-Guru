package guru.springframework.spring_di.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import guru.springframework.spring_di.service.GreetingService;

@Controller
public class Myi18NController {

	private final GreetingService greetingService;

	public Myi18NController(@Qualifier("i18NService") GreetingService greetingService) {

		this.greetingService = greetingService;

	}

	public String sayHello() {

		return greetingService.getGreeting();

	}

}
