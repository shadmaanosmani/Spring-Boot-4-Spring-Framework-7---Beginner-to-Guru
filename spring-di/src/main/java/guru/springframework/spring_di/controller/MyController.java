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

	public void beforeInit() {

		System.out.println("## - Before Init - Called by Bean Post Processor");

	}

	public void afterInit() {

		System.out.println("## - After init called by Bean Post Processor");

	}

}
