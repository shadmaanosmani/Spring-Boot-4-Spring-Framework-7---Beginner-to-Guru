package guru.springframework.spring_di.controller;

import org.springframework.stereotype.Controller;

import guru.springframework.spring_di.service.GreetingService;

@Controller
public class SetterInjectedController {

	private GreetingService greetingService;

	public void setGreetingService(GreetingService greetingService) {

		this.greetingService = greetingService;

	}

	public String sayHello() {

		return greetingService.getGreeting();

	}

}
