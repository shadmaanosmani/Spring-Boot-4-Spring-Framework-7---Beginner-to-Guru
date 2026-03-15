package guru.springframework.spring_di.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import guru.springframework.spring_di.service.GreetingService;

@Controller
public class SetterInjectedController {

	private GreetingService greetingService;

	@Autowired
	@Qualifier("setterInjectedGreetingService")
	public void setGreetingService(GreetingService greetingService) {

		this.greetingService = greetingService;

	}

	public String sayHello() {

		return greetingService.getGreeting();

	}

}
