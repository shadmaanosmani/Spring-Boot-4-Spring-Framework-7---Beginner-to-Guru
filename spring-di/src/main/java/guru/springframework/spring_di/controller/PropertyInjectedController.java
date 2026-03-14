package guru.springframework.spring_di.controller;

import org.springframework.stereotype.Controller;

import guru.springframework.spring_di.service.GreetingService;

@Controller
public class PropertyInjectedController {

	GreetingService greetingService;

	public String sayHello() {

		return greetingService.getGreeting();

	}

}
