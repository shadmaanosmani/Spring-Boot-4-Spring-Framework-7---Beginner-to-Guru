package guru.springframework.spring_di.service.impl;

import org.springframework.stereotype.Service;

import guru.springframework.spring_di.service.GreetingService;

@Service("propertyInjectedGreetingService")
public class GreetingServicePropertyInjected implements GreetingService {

	@Override
	public String getGreeting() {

		return "Friends don't let friends do property injection!!!!";

	}

}
