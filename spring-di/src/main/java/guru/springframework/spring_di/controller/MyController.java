package guru.springframework.spring_di.controller;

import org.springframework.stereotype.Controller;

import guru.springframework.spring_di.service.GreetingService;
import guru.springframework.spring_di.service.impl.GreetingServiceImpl;

@Controller
public class MyController {

	private final GreetingService greetingService;

	public MyController() {

		this.greetingService = new GreetingServiceImpl();

	}

	public String sayHello() {

		System.out.println("I'm in the controller");
		return greetingService.getGreeting();

	}

}
